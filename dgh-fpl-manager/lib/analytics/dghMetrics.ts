import type { EnrichedPlayer, RivalProfile, SquadPick } from "../types";
import type { TeamFixtureRun } from "./fixtures";
import { projectNextGw } from "./projection";
import type { DghLedgerRow } from "../seasonStore";

export type DghPlayerMetrics = {
  hsfi: number;
  bv: number;
  mdi: number;
  wcs: number;
  dtq: number;
  wcps: number;
  miniOwnershipPct: number;
  explosivenessIndex: number;
  recentHaulPct: number;
  xgiVolatility: number;
  fixtureFdr: number;
  estimated: boolean;
};

export type DghTeamMetrics = {
  avgHsfi: number;
  avgWcs: number;
  avgMdi: number;
  avgDtq: number;
  avgWcps: number;
  templateCoveragePct: number;
  swingPotential: number;
  miniLeagueEv: number;
  vbmPct: number | null;
  ldi: number;
  pps: number;
  captainImpact: number | null;
  posture: "ATTACK" | "BALANCED" | "DEFEND";
  strategyState: "DOMINATING" | "ATTACK" | "BALANCED" | "TOO SAFE" | "DISTRESSED";
  bestCaptain: { playerId: number; webName: string; wcps: number; ownershipPct: number } | null;
  viceCaptain: { playerId: number; webName: string; wcps: number; ownershipPct: number } | null;
};

export type PerformanceGate = {
  key: "GW_WIN_RATE" | "TOP3_RATE" | "RELEGATIONS" | "AVG_GDR" | "POSITIVE_TES";
  label: string;
  value: number | null;
  target: number;
  elite: number | null;
  passed: boolean | null;
  status: "PASS" | "ELITE" | "FAIL" | "PENDING";
};

export type PerformanceAudit = {
  sampleGws: number;
  gates: PerformanceGate[];
  failedCount: number;
  wildcardReset: boolean;
  status: "CONTINUE" | "WATCH" | "RESET" | "PENDING";
};

const clamp = (n: number, lo = 0, hi = 100) => Math.max(lo, Math.min(hi, n));
const round = (n: number, dp = 1) => Math.round(n * 10 ** dp) / 10;

function fdrFor(p: EnrichedPlayer, runsByTeam?: Map<number, TeamFixtureRun>): number {
  return runsByTeam?.get(p.teamId)?.next[0]?.difficulty ?? 3;
}

function minutesPct(p: EnrichedPlayer): number {
  const confidence = p.availability.chanceNextRound;
  if (confidence !== null) return clamp(confidence);
  if (p.status === "a") return clamp((p.minutes / 900) * 100);
  return p.status === "d" ? 60 : 20;
}

function setPieceScore(p: EnrichedPlayer): number {
  return (p.setPieces.penalties ? 50 : 0) + (p.setPieces.corners ? 25 : 0) + (p.setPieces.freeKicks ? 25 : 0);
}

function fixtureScore(fdr: number): number { return clamp(((5 - fdr) / 4) * 100); }

/**
 * Canonical weekly-practical HSFI from FPL_2026-27_SYSTEM.md §3.1.
 * The app normalises raw xG/xA per-90 to a 0–100 contribution using 1.0 xG/90
 * as the 100-point reference. This keeps the documented weights bounded while
 * preserving the requested formula and avoiding artificial >100 scores.
 */
export function hsfi(p: EnrichedPlayer, runsByTeam?: Map<number, TeamFixtureRun>): number {
  const xg90 = p.minutes > 0 ? p.xG * 90 / p.minutes : 0;
  const xa90 = p.minutes > 0 ? p.xA * 90 / p.minutes : 0;
  const formScore = clamp(p.form * 20);
  const score = (clamp(xg90 * 100) * 0.25) + (clamp(xa90 * 100) * 0.20) + (formScore * 0.15) + (minutesPct(p) * 0.20) + (setPieceScore(p) * 0.10) + (fixtureScore(fdrFor(p, runsByTeam)) * 0.10);
  return round(clamp(score), 1);
}

function priceTierFactor(price: number): number {
  if (price <= 5.0) return 1.3;
  if (price <= 7.0) return 1.5;
  if (price <= 9.0) return 1.2;
  return 1.0;
}

export function bv(p: EnrichedPlayer, hsfiScore = hsfi(p)): number {
  return round((hsfiScore / Math.max(0.1, p.price)) * (1 - p.ownershipPct / 100), 2);
}

export function mdi(p: EnrichedPlayer, hsfiScore = hsfi(p), explosivenessIndex = 0): number {
  const differentialFactor = 1 + (1 - p.ownershipPct / 100) * 0.5;
  const ceilingMultiplier = 1 + clamp(explosivenessIndex, 0, 1) * 0.3;
  return round((hsfiScore * differentialFactor * ceilingMultiplier) / Math.max(0.1, p.price), 2);
}

/** Available-data proxy used until per-GW element-summary histories are loaded. */
function estimatedExplosiveness(p: EnrichedPlayer): number {
  const formRatio = p.pointsPerGame > 0 ? clamp(p.form / p.pointsPerGame, 0, 2) / 2 : 0.5;
  const xgi = clamp(p.xGI / 1.0, 0, 1);
  return clamp((formRatio * 0.55) + (xgi * 0.45));
}

function estimatedRecentHaulPct(p: EnrichedPlayer): number {
  return clamp(estimatedExplosiveness(p) * 100);
}

function estimatedXgiVolatility(p: EnrichedPlayer): number {
  const mean = Math.max(0.05, p.xGI);
  const spread = Math.abs(p.form - p.pointsPerGame) / Math.max(1, p.pointsPerGame);
  return clamp((spread * 70) + clamp(mean * 25));
}

export function wcs(p: EnrichedPlayer, runsByTeam?: Map<number, TeamFixtureRun>, recentHaulPct?: number, xgiVolatility?: number): number {
  const fdr = fdrFor(p, runsByTeam);
  const haul = recentHaulPct ?? estimatedRecentHaulPct(p);
  const volatility = xgiVolatility ?? estimatedXgiVolatility(p);
  return round(clamp((haul * 0.40) + (volatility * 0.30) + (fixtureScore(fdr) * 0.30)), 1);
}

export function dtq(p: EnrichedPlayer, hsfiScore = hsfi(p)): number {
  return round(((100 - p.ownershipPct) * hsfiScore * priceTierFactor(p.price)) / 100, 1);
}

export function ownershipBonus(ownershipPct: number): number {
  if (ownershipPct < 30) return 30;
  if (ownershipPct <= 50) return 15;
  if (ownershipPct <= 70) return 0;
  return -20;
}

export function wcps(p: EnrichedPlayer, hsfiScore = hsfi(p), wcsScore = wcs(p), miniOwnershipPct = p.ownershipPct): number {
  return round(clamp((hsfiScore * 0.4) + (wcsScore * 0.3) + (ownershipBonus(miniOwnershipPct) * 0.3)), 1);
}

export function playerDghMetrics(p: EnrichedPlayer, options: { runsByTeam?: Map<number, TeamFixtureRun>; miniOwnershipPct?: number; recentHaulPct?: number; xgiVolatility?: number; explosivenessIndex?: number } = {}): DghPlayerMetrics {
  const h = hsfi(p, options.runsByTeam);
  const wi = wcs(p, options.runsByTeam, options.recentHaulPct, options.xgiVolatility);
  const ex = options.explosivenessIndex ?? estimatedExplosiveness(p);
  const own = options.miniOwnershipPct ?? p.ownershipPct;
  return {
    hsfi: h,
    bv: bv(p, h),
    mdi: mdi(p, h, ex),
    wcs: wi,
    dtq: dtq({ ...p, ownershipPct: own }, h),
    wcps: wcps({ ...p, ownershipPct: own }, h, wi, own),
    miniOwnershipPct: own,
    explosivenessIndex: ex,
    recentHaulPct: options.recentHaulPct ?? estimatedRecentHaulPct(p),
    xgiVolatility: options.xgiVolatility ?? estimatedXgiVolatility(p),
    fixtureFdr: fdrFor(p, options.runsByTeam),
    estimated: options.recentHaulPct == null || options.xgiVolatility == null || options.explosivenessIndex == null,
  };
}

export function buildTeamMetrics(input: {
  squad: SquadPick[];
  rivals: RivalProfile[];
  playerMetrics?: Map<number, DghPlayerMetrics>;
  weeklyScores?: number[];
  currentPoints?: number;
  remainingPlayers?: number;
  avgExpected?: number;
  autosubImpact?: number;
  captainPoints?: number;
  templateCaptainAvg?: number;
}): DghTeamMetrics {
  const xi = input.squad.filter((p) => p.isXI);
  const metrics = xi.map((p) => input.playerMetrics?.get(p.playerId) ?? playerDghMetrics(p.player));
  const avg = (key: keyof Pick<DghPlayerMetrics, "hsfi" | "wcs" | "mdi" | "dtq" | "wcps">) => metrics.length ? metrics.reduce((s, m) => s + Number(m[key]), 0) / metrics.length : 0;
  const templateCoveragePct = xi.length ? round((xi.filter((p) => (input.playerMetrics?.get(p.playerId)?.miniOwnershipPct ?? p.player.ownershipPct) > 50).length / xi.length) * 100) : 0;
  const n = Math.max(1, input.rivals.length + 1);
  const swingPotential = round(xi.reduce((s, p) => { const m = input.playerMetrics?.get(p.playerId); const own = m?.miniOwnershipPct ?? p.player.ownershipPct; return s + ((n - Math.round((own / 100) * n)) / n) * (m?.wcs ?? wcs(p.player)) * 0.1; }, 0));
  const miniLeagueEv = round(xi.reduce((s, p) => { const own = input.playerMetrics?.get(p.playerId)?.miniOwnershipPct ?? p.player.ownershipPct; return s + ((n - Math.round((own / 100) * n)) * p.player.form); }, 0));
  const avgScore = input.weeklyScores?.length ? input.weeklyScores.reduce((a, b) => a + b, 0) / input.weeklyScores.length : null;
  const variance = input.weeklyScores?.length && avgScore ? Math.sqrt(input.weeklyScores.reduce((s, x) => s + (x - avgScore) ** 2, 0) / input.weeklyScores.length) : null;
  const vbmPct = variance != null && avgScore ? round((variance / avgScore) * 100) : null;
  const ldi = round(xi.reduce((s, p) => {
    const rivalPositionPoints = input.rivals.flatMap(r => (r.squad ?? []).filter(q => q.player.position === p.player.position).map(q => q.livePoints));
    const leagueAvgAtPosition = rivalPositionPoints.length ? rivalPositionPoints.reduce((a, b) => a + b, 0) / rivalPositionPoints.length : 0;
    const rivalOwnership = input.rivals.length ? input.rivals.filter((r) => r.squad?.some((q) => q.playerId === p.playerId)).length / input.rivals.length * 100 : p.player.ownershipPct;
    const ownershipDifferential = (100 - rivalOwnership) / 100;
    return s + (p.livePoints - leagueAvgAtPosition) * ownershipDifferential;
  }, 0));
  const pps = round((input.currentPoints ?? 0) + Math.max(0, input.remainingPlayers ?? 0) * (input.avgExpected ?? 0) + (input.autosubImpact ?? 0));
  const captainImpact = input.captainPoints != null && input.templateCaptainAvg != null ? round((input.captainPoints * 2) - (input.templateCaptainAvg * 2)) : null;
  const captainRanked = xi.map(p => ({ player: p.player, score: input.playerMetrics?.get(p.playerId)?.wcps ?? wcps(p.player) })).sort((a, b) => b.score - a.score);
  const bestCaptain = captainRanked[0] ? { playerId: captainRanked[0].player.id, webName: captainRanked[0].player.webName, wcps: round(captainRanked[0].score), ownershipPct: captainRanked[0].player.ownershipPct } : null;
  const viceCaptain = captainRanked[1] ? { playerId: captainRanked[1].player.id, webName: captainRanked[1].player.webName, wcps: round(captainRanked[1].score), ownershipPct: captainRanked[1].player.ownershipPct } : null;
  const posture = swingPotential >= 22 ? "ATTACK" : templateCoveragePct > 80 || (vbmPct != null && vbmPct > 40) ? "DEFEND" : "BALANCED";
  const strategyState = vbmPct != null && vbmPct > 40 ? "DISTRESSED" : swingPotential >= 22 && templateCoveragePct <= 80 ? "DOMINATING" : swingPotential >= 15 ? "ATTACK" : templateCoveragePct > 80 ? "TOO SAFE" : "BALANCED";
  return { avgHsfi: round(avg("hsfi")), avgWcs: round(avg("wcs")), avgMdi: round(avg("mdi")), avgDtq: round(avg("dtq")), avgWcps: round(avg("wcps")), templateCoveragePct, swingPotential, miniLeagueEv, vbmPct, ldi, pps, captainImpact, posture, strategyState, bestCaptain, viceCaptain };
}

/**
 * Gameweek Dominance Rating for a single ledger row, per FPL_2026-27_SYSTEM.md
 * §GDR: Finish_Points (1st=100, 2nd=75, 3rd=50, 4th–8th=25, 9th+=0) +
 * Top3_Bonus (+20 if podium) − Template_Penalty − Relegation_Risk.
 * Template_Penalty and Relegation_Risk are always 0 here: per-XI ownership
 * and mini-league-specific relegation rank are not persisted in the ledger,
 * and this app never infers them from unrelated fields — see
 * evaluatePerformanceGates's original comment for the same rule. Shared by
 * evaluatePerformanceGates (season-average gate) and buildGdrLog (the
 * per-GW log a manager can actually read GW-by-GW).
 */
function gdrForRow(overallRank: number | null): number {
  const rank = overallRank ?? 999;
  const finish = rank === 1 ? 100 : rank === 2 ? 75 : rank === 3 ? 50 : rank <= 8 ? 25 : 0;
  const top3 = rank <= 3 ? 20 : 0;
  return finish + top3;
}

export type GdrLogEntry = { event: number; gdr: number; overallRank: number | null; rawPoints: number };

/** Per-GW GDR log for one manager, oldest-first — every ledger row that
 * exists for them, not just the trailing-10 window evaluatePerformanceGates
 * uses for its gate. Reads directly from the already-persisted DGH ledger
 * (lib/seasonStore.ts's dgh_ledger_gw table via getAllDghLedgerRows), so
 * this needs no new storage of its own. */
export function buildGdrLog(rows: DghLedgerRow[], entryId: number): GdrLogEntry[] {
  return rows
    .filter((r) => r.entryId === entryId)
    .sort((a, b) => a.event - b.event)
    .map((r) => ({ event: r.event, gdr: gdrForRow(r.overallRank), overallRank: r.overallRank, rawPoints: r.rawPoints }));
}

export function evaluatePerformanceGates(rows: DghLedgerRow[], entryId: number, transferTes: number[] = []): PerformanceAudit {
  const own = rows.filter((r) => r.entryId === entryId).sort((a, b) => a.event - b.event).slice(-10);
  if (!own.length) return { sampleGws: 0, gates: [], failedCount: 0, wildcardReset: false, status: "PENDING" };
  const gdrs = own.map((r) => gdrForRow(r.overallRank));
  const wins = own.filter((r) => r.overallRank === 1).length;
  const top3 = own.filter((r) => (r.overallRank ?? 999) <= 3).length;
  const avgGdr = gdrs.reduce((a, b) => a + b, 0) / gdrs.length;
  const positiveTes = transferTes.length ? transferTes.filter((x) => x > 0).length / transferTes.length : null;
  const make = (key: PerformanceGate["key"], label: string, value: number | null, target: number, elite: number | null): PerformanceGate => {
    if (value == null) return { key, label, value, target, elite, passed: null, status: "PENDING" };
    const passed = key === "RELEGATIONS" ? value <= target : value >= target;
    const isElite = elite != null && (key === "RELEGATIONS" ? value <= elite : value >= elite);
    return { key, label, value: round(value), target, elite, passed, status: isElite ? "ELITE" : passed ? "PASS" : "FAIL" };
  };
  const gates = [
    make("GW_WIN_RATE", "GW win rate", wins / own.length, 0.1, 0.2),
    make("TOP3_RATE", "Top-3 rate", top3 / own.length, 0.3, 0.4),
    make("RELEGATIONS", "Relegations", null, 1, 0),
    make("AVG_GDR", "Average GDR", avgGdr, 40, 55),
    make("POSITIVE_TES", "Positive TES", positiveTes, 0.5, 0.65),
  ];
  const failedCount = gates.filter((g) => g.status === "FAIL").length;
  const pending = gates.some((g) => g.status === "PENDING");
  return { sampleGws: own.length, gates, failedCount, wildcardReset: failedCount >= 3, status: pending ? "PENDING" : failedCount >= 3 ? "RESET" : failedCount > 0 ? "WATCH" : "CONTINUE" };
}
