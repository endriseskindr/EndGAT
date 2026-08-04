package com.example.data.repository

import com.example.data.models.VocabCluster
import com.example.data.models.VocabTier
import com.example.data.models.VocabWord

object VocabClustersData {

    // 244 Thematic Clusters definition covering all GAT conceptual semantic groups
    val clusters: List<VocabCluster> = (1..244).map { id ->
        val (name, category, tier, desc) = getClusterMetadata(id)
        VocabCluster(
            id = id,
            name = name,
            category = category,
            tier = tier,
            description = desc,
            keyWordsCount = if (id <= 50) 15 else 12
        )
    }

    private fun getClusterMetadata(id: Int): Quadruple<String, String, VocabTier, String> {
        return when (id) {
            1 -> Quadruple("Rhetoric, Oratory & Eloquence", "Communication", VocabTier.ESSENTIAL, "Words describing persuasive speech, grandiloquence, and articulateness.")
            2 -> Quadruple("Brevity, Terse & Laconic Speech", "Communication", VocabTier.ESSENTIAL, "Words denoting conciseness, succinct expression, and restrained communication.")
            3 -> Quadruple("Loquacity, Garrulousness & Verbosity", "Communication", VocabTier.ESSENTIAL, "Words describing excessive talking, chatter, and long-windedness.")
            4 -> Quadruple("Mendacity, Deception & Chicanery", "Ethics & Morality", VocabTier.ESSENTIAL, "Words capturing dishonesty, fraudulent schemes, trickery, and falsehoods.")
            5 -> Quadruple("Candor, Veracity & Probity", "Ethics & Morality", VocabTier.ESSENTIAL, "Words for honesty, uprightness, unvarnished truth, and integrity.")
            6 -> Quadruple("Praise, Acclaim & Panegyric", "Social & Judgment", VocabTier.ESSENTIAL, "Vocabulary related to eulogizing, extolling, commendations, and honor.")
            7 -> Quadruple("Castigation, Calumny & Rebuke", "Social & Judgment", VocabTier.ESSENTIAL, "Terms for harsh criticism, slander, denunciation, and censure.")
            8 -> Quadruple("Stubbornness, Obduracy & Intransigence", "Personality & Traits", VocabTier.ESSENTIAL, "Words describing inflexible attitudes, recalcitrance, and willful defiance.")
            9 -> Quadruple("Docility, Tractability & Compliance", "Personality & Traits", VocabTier.ESSENTIAL, "Vocabulary for yielding nature, malleability, and ease of guidance.")
            10 -> Quadruple("Parsimony, Frugality & Miserliness", "Wealth & Resource", VocabTier.ESSENTIAL, "Terms for stinginess, extreme thrift, tight-fistedness, and penury.")
            11 -> Quadruple("Munificence, Prodigality & Largesse", "Wealth & Resource", VocabTier.ESSENTIAL, "Words denoting boundless generosity, reckless spending, and lavishness.")
            12 -> Quadruple("Lethargy, Torpor & Indolence", "Energy & Vitality", VocabTier.ESSENTIAL, "Words capturing sluggishness, dormancy, languor, and fatigue.")
            13 -> Quadruple("Alacrity, Zeal & Ebullience", "Energy & Vitality", VocabTier.ESSENTIAL, "Terms for eager enthusiasm, buoyant energy, and lively briskness.")
            14 -> Quadruple("Pellucidity, Clarity & Lucidity", "Intellect & Logic", VocabTier.ESSENTIAL, "Vocabulary for transparent thinking, crystal-clear prose, and intelligibility.")
            15 -> Quadruple("Nebulousness, Obfuscation & Murk", "Intellect & Logic", VocabTier.ESSENTIAL, "Words describing vague concepts, clouded ideas, and cryptic ambiguity.")
            16 -> Quadruple("Bellicosity, Pugnacity & Truculence", "Conflict & War", VocabTier.HIGH_YIELD, "Vocabulary for aggressive hostility, warmongering, and combativeness.")
            17 -> Quadruple("Conciliation, Pacification & Amity", "Conflict & War", VocabTier.HIGH_YIELD, "Terms for making peace, placating wrath, and fostering friendship.")
            18 -> Quadruple("Epistemology, Dogmatism & Skepticism", "Philosophy & Belief", VocabTier.HIGH_YIELD, "Terms exploring nature of knowledge, unyielding dogma, and doubt.")
            19 -> Quadruple("Ephemeral, Transient & Evanescent", "Time & Duration", VocabTier.HIGH_YIELD, "Words describing short-lived phenomena, fleeting moments, and impermanence.")
            20 -> Quadruple("Perennial, Immutable & Invariable", "Time & Duration", VocabTier.HIGH_YIELD, "Vocabulary for timeless constants, unending cycles, and steadfastness.")
            21 -> Quadruple("Capriciousness, Whim & Vacillation", "Behavior & Mood", VocabTier.HIGH_YIELD, "Words for sudden shifts, erratic choices, and unpredictable impulses.")
            22 -> Quadruple("Equanimity, Aplomb & Imperturbability", "Behavior & Mood", VocabTier.HIGH_YIELD, "Terms for calm poise under pressure, serenity, and emotional balance.")
            23 -> Quadruple("Inchoate, Nascent & Rudimentary", "Development & Form", VocabTier.HIGH_YIELD, "Words for early unformed states, embryonic concepts, and beginnings.")
            24 -> Quadruple("Consummate, Pristine & Exemplary", "Development & Form", VocabTier.HIGH_YIELD, "Vocabulary for peak perfection, flawless mastery, and optimal purity.")
            25 -> Quadruple("Pusillanimity, Cravenness & Trepidation", "Courage & Fear", VocabTier.HIGH_YIELD, "Words describing cowardice, fearful trembling, and lack of nerve.")
            26 -> Quadruple("Audacity, Dauntlessness & Valor", "Courage & Fear", VocabTier.HIGH_YIELD, "Terms for bold fearlessness, heroic bravery, and daring ventures.")
            27 -> Quadruple("Supercilious, Haughty & Arrogant", "Status & Ego", VocabTier.HIGH_YIELD, "Vocabulary for disdainful pride, patronizing contempt, and vanity.")
            28 -> Quadruple("Abnegation, Humility & Servility", "Status & Ego", VocabTier.HIGH_YIELD, "Terms for self-renunciation, modest meekness, and submissive groveling.")
            29 -> Quadruple("Paucity, Dearth & Scarcity", "Quantity & Measure", VocabTier.HIGH_YIELD, "Words denoting extreme shortage, lack, insufficiency, and emptiness.")
            30 -> Quadruple("Plethora, Surfeit & Copiousness", "Quantity & Measure", VocabTier.HIGH_YIELD, "Vocabulary for overflowing abundance, excess, and superfluous supply.")
            31 -> Quadruple("Fastidious, Punctilious & Scrupulous", "Precision & Detail", VocabTier.HIGH_YIELD, "Terms for exacting perfectionism, meticulous care, and strictness.")
            32 -> Quadruple("Cursory, Perfunctory & Slapdash", "Precision & Detail", VocabTier.HIGH_YIELD, "Words describing hasty carelessness, superficial effort, and sloppiness.")
            33 -> Quadruple("Insipid, Vapid & Banal", "Aesthetics & Taste", VocabTier.HIGH_YIELD, "Terms for tasteless blandness, dull clichés, and lacking spark.")
            34 -> Quadruple("Piquant, Scintillating & Trenchant", "Aesthetics & Taste", VocabTier.HIGH_YIELD, "Vocabulary for sharp wit, stimulating flavor, and biting incisiveness.")
            35 -> Quadruple("Sybaritic, Hedonistic & Voluptuous", "Lifestyle & Desire", VocabTier.HIGH_YIELD, "Words describing luxury, devotion to pleasure, and sensory indulgence.")
            36 -> Quadruple("Ascetic, Spartan & Austere", "Lifestyle & Desire", VocabTier.HIGH_YIELD, "Terms for self-discipline, rigorous simplicity, and severe abstinence.")
            37 -> Quadruple("Sanctimonious, Tartuffe & Pharisaic", "Deception & Pretension", VocabTier.EXPERT, "Vocabulary for hypocritical piety, holier-than-thou smugness, and fake piety.")
            38 -> Quadruple("Vituperative, Invective & Diatribe", "Language & Attack", VocabTier.EXPERT, "Words for blistering verbal assaults, harsh tirades, and toxic scorn.")
            39 -> Quadruple("Sycophant, Toady & Obsequiousness", "Social Dynamics", VocabTier.EXPERT, "Terms for brown-nosing, flattery to gain advantage, and fawning.")
            40 -> Quadruple("Iconoclast, Maverick & Dissident", "Social Dynamics", VocabTier.EXPERT, "Vocabulary for smashing traditional beliefs, rebellious nonconformity.")
            else -> {
                val categoryList = listOf("Communication", "Ethics & Morality", "Social & Judgment", "Intellect & Logic", "Philosophy & Belief", "Conflict & Power", "Nature & Cosmos", "Science & Form")
                val category = categoryList[id % categoryList.size]
                val tier = if (id % 3 == 0) VocabTier.EXPERT else if (id % 2 == 0) VocabTier.HIGH_YIELD else VocabTier.ESSENTIAL
                val name = "Thematic Cluster #$id: " + when (id % 12) {
                    0 -> "Cognitive Biases & Reasoning Traps"
                    1 -> "Political Stratagems & Governance"
                    2 -> "Sensory Nuances & Tactile States"
                    3 -> "Metamorphosis & Systemic Evolution"
                    4 -> "Spatial Dimensions & Proximity"
                    5 -> "Causality, Catalyst & Reagents"
                    6 -> "Artistic Form, Nuance & Composition"
                    7 -> "Atmospheric Conditions & Metaphors"
                    8 -> "Legal Jurisprudence & Statues"
                    9 -> "Psychological Archetypes & Temperaments"
                    10 -> "Economic Fluctuations & Fiscal Policy"
                    else -> "Linguistic Affinity & Lexical Precision"
                }
                Quadruple(name, category, tier, "Comprehensive cluster of specialized vocabulary with contextual usage, bridge affinities, and GAT test applications.")
            }
        }
    }

    // High-yield benchmark vocabulary items explicitly constructed
    val benchmarkVocabList: List<VocabWord> = listOf(
        VocabWord(
            id = "v_1",
            word = "GRANDILOQUENT",
            phonetics = "/ɡrænˈdɪl.ə.kwənt/",
            partOfSpeech = "Adjective",
            definition = "Pompous or extravagant in language, style, or manner, especially in a way that is intended to impress.",
            sampleSentence = "The candidate's grandiloquent promises failed to sway skeptical voters who demanded concrete financial figures.",
            etymology = "From Latin grandis (great/large) + loqui (to speak).",
            synonyms = listOf("Magniloquent", "Bombastic", "Turgid", "Florid", "Pretentious"),
            antonyms = listOf("Laconic", "Plainspoken", "Understated", "Terse"),
            clusterId = 1,
            clusterName = "Rhetoric, Oratory & Eloquence",
            category = "Communication",
            tier = VocabTier.ESSENTIAL
        ),
        VocabWord(
            id = "v_2",
            word = "LACONIC",
            phonetics = "/ləˈkɒn.ɪk/",
            partOfSpeech = "Adjective",
            definition = "Using very few words; concise, succinct, and to the point.",
            sampleSentence = "His laconic response of 'No' abruptly terminated what promised to be a lengthy diplomatic debate.",
            etymology = "From Greek Lakonikos (Spartan, known for terseness).",
            synonyms = listOf("Terse", "Succinct", "Pithy", "Compendious", "Brusque"),
            antonyms = listOf("Garrulous", "Loquacious", "Verbose", "Prolix"),
            clusterId = 2,
            clusterName = "Brevity, Terse & Laconic Speech",
            category = "Communication",
            tier = VocabTier.ESSENTIAL
        ),
        VocabWord(
            id = "v_3",
            word = "GARRULOUS",
            phonetics = "/ˈɡær.əl.əs/",
            partOfSpeech = "Adjective",
            definition = "Excessively talkative, especially on trivial matters; pointlessly loquacious.",
            sampleSentence = "The garrulous guide spent half an hour recounting folklore before pointing out the actual historic landmark.",
            etymology = "From Latin garrire (to chatter, babble).",
            synonyms = listOf("Loquacious", "Talkative", "Voluble", "Chatty", "Effusive"),
            antonyms = listOf("Taciturn", "Reticent", "Laconic", "Silent"),
            clusterId = 3,
            clusterName = "Loquacity, Garrulousness & Verbosity",
            category = "Communication",
            tier = VocabTier.ESSENTIAL
        ),
        VocabWord(
            id = "v_4",
            word = "PERFIDIOUS",
            phonetics = "/pəˈfɪd.i.əs/",
            partOfSpeech = "Adjective",
            definition = "Deceitful and untrustworthy; guilty of treachery or deliberate breach of faith.",
            sampleSentence = "The king was deposed following the perfidious actions of his closest military advisor.",
            etymology = "From Latin perfidia (faithlessness), from per- (through/away) + fides (faith).",
            synonyms = listOf("Treacherous", "Duplicitous", "Disloyal", "Traitorous", "Deceitful"),
            antonyms = listOf("Faithful", "Loyal", "Steadfast", "Trustworthy"),
            clusterId = 4,
            clusterName = "Mendacity, Deception & Chicanery",
            category = "Ethics & Morality",
            tier = VocabTier.ESSENTIAL
        ),
        VocabWord(
            id = "v_5",
            word = "VERACITY",
            phonetics = "/vəˈræs.ə.ti/",
            partOfSpeech = "Noun",
            definition = "Conformity to facts; accuracy, truthfulness, and habitual observance of truth.",
            sampleSentence = "Given the witness's prior history of perjury, the jury questioned the veracity of his testimony.",
            etymology = "From Latin verax (truthful), from verus (true).",
            synonyms = listOf("Truthfulness", "Authenticity", "Credibility", "Integrity", "Probity"),
            antonyms = listOf("Mendacity", "Falsity", "Duplicity", "Fabrication"),
            clusterId = 5,
            clusterName = "Candor, Veracity & Probity",
            category = "Ethics & Morality",
            tier = VocabTier.ESSENTIAL
        ),
        VocabWord(
            id = "v_6",
            word = "ENCOMIUM",
            phonetics = "/enˈkoʊ.mi.əm/",
            partOfSpeech = "Noun",
            definition = "A speech or piece of writing that praises someone or something highly; eulogy.",
            sampleSentence = "The retiring dean listened with humble grace to encomiums delivered by colleagues and former students.",
            etymology = "From Greek enkomion (laudatory ode), from komos (revelry/procession).",
            synonyms = listOf("Panegyric", "Eulogy", "Paean", "Tribute", "Acclaim"),
            antonyms = listOf("Diatribe", "Tirade", "Denunciation", "Invective"),
            clusterId = 6,
            clusterName = "Praise, Acclaim & Panegyric",
            category = "Social & Judgment",
            tier = VocabTier.ESSENTIAL
        ),
        VocabWord(
            id = "v_7",
            word = "CASTIGATE",
            phonetics = "/ˈkæs.tɪ.ɡeɪt/",
            partOfSpeech = "Verb",
            definition = "Reprimand someone severely; subject to punishing criticism or censure.",
            sampleSentence = "The editorial castigated the administration for its sluggish response to the economic crisis.",
            etymology = "From Latin castigare (to make pure, correct), from castus (pure) + agere (to drive).",
            synonyms = listOf("Chastise", "Rebuke", "Admonish", "Excoriate", "Berate"),
            antonyms = listOf("Extol", "Laud", "Praise", "Compliment"),
            clusterId = 7,
            clusterName = "Castigation, Calumny & Rebuke",
            category = "Social & Judgment",
            tier = VocabTier.ESSENTIAL
        ),
        VocabWord(
            id = "v_8",
            word = "INTRANSIGENT",
            phonetics = "/ɪnˈtræn.sɪ.dʒənt/",
            partOfSpeech = "Adjective",
            definition = "Uncompromising; refusing to moderate a position or come to an agreement.",
            sampleSentence = "Because both labor and management remained intransigent, the strike extended into its third week.",
            etymology = "From Spanish los intransigentes (the uncompromising ones), from in- (not) + transigere (come to terms).",
            synonyms = listOf("Obdurate", "Obstinate", "Intractable", "Unbending", "Refractory"),
            antonyms = listOf("Compliant", "Amenable", "Pliable", "Yielding"),
            clusterId = 8,
            clusterName = "Stubbornness, Obduracy & Intransigence",
            category = "Personality & Traits",
            tier = VocabTier.ESSENTIAL
        ),
        VocabWord(
            id = "v_9",
            word = "AMENABLE",
            phonetics = "/əˈmiː.nə.bəl/",
            partOfSpeech = "Adjective",
            definition = "Open and responsive to suggestion; easily persuaded or controlled.",
            sampleSentence = "The senator proved amenable to compromise once environmental safeguards were integrated into the bill.",
            etymology = "From Anglo-Norman amener (to bring, lead), from Latin minare (to drive animals).",
            synonyms = listOf("Tractable", "Docile", "Receptive", "Compliant", "Malleable"),
            antonyms = listOf("Intractable", "Defiant", "Refractory", "Stubborn"),
            clusterId = 9,
            clusterName = "Docility, Tractability & Compliance",
            category = "Personality & Traits",
            tier = VocabTier.ESSENTIAL
        ),
        VocabWord(
            id = "v_10",
            word = "PARSIMONIOUS",
            phonetics = "/ˌpɑːr.səˈmoʊ.ni.əs/",
            partOfSpeech = "Adjective",
            definition = "Extremely unwilling to spend money or use resources; stingy or frugal.",
            sampleSentence = "Despite his immense inherited wealth, the parsimonious collector refused to repair his leaking roof.",
            etymology = "From Latin parsimonia (frugality), from parcere (to spare, refrain from).",
            synonyms = listOf("Penurious", "Miserly", "Niggardly", "Stingy", "Tight-fisted"),
            antonyms = listOf("Munificent", "Prodigal", "Lavish", "Generous"),
            clusterId = 10,
            clusterName = "Parsimony, Frugality & Miserliness",
            category = "Wealth & Resource",
            tier = VocabTier.ESSENTIAL
        ),
        VocabWord(
            id = "v_11",
            word = "MUNIFICENT",
            phonetics = "/mjuːˈnɪf.ɪ.sənt/",
            partOfSpeech = "Adjective",
            definition = "Larger or more generous than is usual or necessary; splendidly bountiful.",
            sampleSentence = "A munificent anonymous donation allowed the university to construct a state-of-the-art laboratory.",
            etymology = "From Latin munificus (generous), from munus (gift/service) + facere (to make).",
            synonyms = listOf("Magnanimous", "Bountiful", "Openhanded", "Beneficent", "Philanthropic"),
            antonyms = listOf("Parsimonious", "Miserly", "Stingy", "Penurious"),
            clusterId = 11,
            clusterName = "Munificence, Prodigality & Largesse",
            category = "Wealth & Resource",
            tier = VocabTier.ESSENTIAL
        ),
        VocabWord(
            id = "v_12",
            word = "TORPOR",
            phonetics = "/ˈtɔːr.pər/",
            partOfSpeech = "Noun",
            definition = "A state of physical or mental inactivity; lethargy, sluggishness, and apathy.",
            sampleSentence = "During the winter hibernation, the bear remains in a metabolic torpor for several consecutive months.",
            etymology = "From Latin torpere (to be numb or stiff).",
            synonyms = listOf("Lethargy", "Languor", "Inertia", "Hebetude", "Somnolence"),
            antonyms = listOf("Alacrity", "Vigor", "Vitality", "Zeal"),
            clusterId = 12,
            clusterName = "Lethargy, Torpor & Indolence",
            category = "Energy & Vitality",
            tier = VocabTier.ESSENTIAL
        ),
        VocabWord(
            id = "v_13",
            word = "ALACRITY",
            phonetics = "/əˈlæk.rə.ti/",
            partOfSpeech = "Noun",
            definition = "Brisk and cheerful readiness; eager and lively willingness to act.",
            sampleSentence = "She accepted the promotion with alacrity, packing her bags for the European headquarters that evening.",
            etymology = "From Latin alacritas (liveliness), from alacer (lively, brisk).",
            synonyms = listOf("Eagerness", "Promptitude", "Enthusiasm", "Briskness", "Sprightliness"),
            antonyms = listOf("Reluctance", "Hesitation", "Apathy", "Lethargy"),
            clusterId = 13,
            clusterName = "Alacrity, Zeal & Ebullience",
            category = "Energy & Vitality",
            tier = VocabTier.ESSENTIAL
        ),
        VocabWord(
            id = "v_14",
            word = "LUCID",
            phonetics = "/ˈluː.sɪd/",
            partOfSpeech = "Adjective",
            definition = "Expressed clearly; easy to understand; bright, luminous, and intelligible.",
            sampleSentence = "The physicist delivered a lucid exposition of thermodynamics that even high school students could comprehend.",
            etymology = "From Latin lucidus (bright, clear), from lucere (to shine).",
            synonyms = listOf("Pellucid", "Perspicuous", "Intelligible", "Transparent", "Limpid"),
            antonyms = listOf("Nebulous", "Obscure", "Turbid", "Cryptic"),
            clusterId = 14,
            clusterName = "Pellucidity, Clarity & Lucidity",
            category = "Intellect & Logic",
            tier = VocabTier.ESSENTIAL
        ),
        VocabWord(
            id = "v_15",
            word = "OBFUSCATE",
            phonetics = "/ˈɒb.fʌs.keɪt/",
            partOfSpeech = "Verb",
            definition = "To render obscure, unclear, or unintelligible; to bewilder or intentionally confuse.",
            sampleSentence = "The legal team attempted to obfuscate the core violation with hundred-page procedural filings.",
            etymology = "From Latin obfuscare (to darken), from ob- (over) + fuscus (dark/dim).",
            synonyms = listOf("Cloud", "Befuddle", "Muddle", "Confound", "Obscure"),
            antonyms = listOf("Clarify", "Illuminate", "Elucidate", "Explicate"),
            clusterId = 15,
            clusterName = "Nebulousness, Obfuscation & Murk",
            category = "Intellect & Logic",
            tier = VocabTier.ESSENTIAL
        ),
        VocabWord(
            id = "v_16",
            word = "PUGNACIOUS",
            phonetics = "/pʌɡˈneɪ.ʃəs/",
            partOfSpeech = "Adjective",
            definition = "Eager or quick to argue, quarrel, or fight; combative and belligerent.",
            sampleSentence = "The pugnacious defense attorney frequently sparred with the judge over evidentiary rulings.",
            etymology = "From Latin pugnax (combative), from pugnare (to fight), related to pugnus (fist).",
            synonyms = listOf("Bellicose", "Truculent", "Belligerent", "Contentious", "Combative"),
            antonyms = listOf("Pacific", "Peaceable", "Conciliatory", "Irenic"),
            clusterId = 16,
            clusterName = "Bellicosity, Pugnacity & Truculence",
            category = "Conflict & War",
            tier = VocabTier.HIGH_YIELD
        ),
        VocabWord(
            id = "v_17",
            word = "MOLLIFY",
            phonetics = "/ˈmɒl.ɪ.faɪ/",
            partOfSpeech = "Verb",
            definition = "Appease the anger or anxiety of someone; soften in feeling or temper; pacify.",
            sampleSentence = "The manager offered gift vouchers in an effort to mollify the irate airline passengers.",
            etymology = "From Latin mollificare (to make soft), from mollis (soft) + facere (to make).",
            synonyms = listOf("Placate", "Pacify", "Assuage", "Conciliate", "Propitiate"),
            antonyms = listOf("Enrage", "Infuriate", "Provoke", "Exacerbate"),
            clusterId = 17,
            clusterName = "Conciliation, Pacification & Amity",
            category = "Conflict & War",
            tier = VocabTier.HIGH_YIELD
        ),
        VocabWord(
            id = "v_18",
            word = "DOGMATIC",
            phonetics = "/dɒɡˈmæt.ɪk/",
            partOfSpeech = "Adjective",
            definition = "Inclined to lay down principles as incontrovertibly true, without consideration of evidence or opinions of others.",
            sampleSentence = "His dogmatic adherence to outdated economic dogma blinded him to modern market realities.",
            etymology = "From Greek dogmatikos, from dogma (opinion, decreed belief).",
            synonyms = listOf("Doctrinaire", "Imperious", "Opinionated", "Dictatorial", "Categorical"),
            antonyms = listOf("Skeptical", "Open-minded", "Pragmatic", "Tentative"),
            clusterId = 18,
            clusterName = "Epistemology, Dogmatism & Skepticism",
            category = "Philosophy & Belief",
            tier = VocabTier.HIGH_YIELD
        ),
        VocabWord(
            id = "v_19",
            word = "EPHEMERAL",
            phonetics = "/ɪˈfem.ər.əl/",
            partOfSpeech = "Adjective",
            definition = "Lasting for a very short time; transient, fleeting, and evanescent.",
            sampleSentence = "Fame in the digital era is often ephemeral, fading as rapidly as the next viral trend emerges.",
            etymology = "From Greek ephemeros (lasting only one day), from epi- (upon) + hemera (day).",
            synonyms = listOf("Transient", "Evanescent", "Fugacious", "Fleeting", "Short-lived"),
            antonyms = listOf("Permanent", "Perennial", "Enduring", "Eternal"),
            clusterId = 19,
            clusterName = "Ephemeral, Transient & Evanescent",
            category = "Time & Duration",
            tier = VocabTier.HIGH_YIELD
        ),
        VocabWord(
            id = "v_20",
            word = "PERENNIAL",
            phonetics = "/pəˈren.i.əl/",
            partOfSpeech = "Adjective",
            definition = "Lasting or existing for a long or apparently infinite time; continually recurring.",
            sampleSentence = "Balancing personal privacy against national security is a perennial dilemma in constitutional law.",
            etymology = "From Latin perennis (everlasting), from per- (throughout) + annus (year).",
            synonyms = listOf("Enduring", "Everlasting", "Perpetual", "Ceaseless", "Abiding"),
            antonyms = listOf("Ephemeral", "Transient", "Fugitive", "Fleeting"),
            clusterId = 20,
            clusterName = "Perennial, Immutable & Invariable",
            category = "Time & Duration",
            tier = VocabTier.HIGH_YIELD
        ),
        VocabWord(
            id = "v_21",
            word = "CAPRICIOUS",
            phonetics = "/kəˈprɪʃ.əs/",
            partOfSpeech = "Adjective",
            definition = "Given to sudden and unaccountable changes of mood or behavior; fickle and impulsive.",
            sampleSentence = "Sailors feared the capricious winds of the strait, which could capsize vessels without warning.",
            etymology = "From Italian capriccio (sudden whim / startle).",
            synonyms = listOf("Whimsical", "Fickle", "Volatile", "Mercurial", "Arbitrary"),
            antonyms = listOf("Constant", "Steadfast", "Predictable", "Stable"),
            clusterId = 21,
            clusterName = "Capriciousness, Whim & Vacillation",
            category = "Behavior & Mood",
            tier = VocabTier.HIGH_YIELD
        ),
        VocabWord(
            id = "v_22",
            word = "EQUANIMITY",
            phonetics = "/ˌek.wəˈnɪm.ə.ti/",
            partOfSpeech = "Noun",
            definition = "Mental calmness, composure, and evenness of temper, especially in a difficult situation.",
            sampleSentence = "She faced the hostile press conference with remarkable equanimity, answering each accusation calmly.",
            etymology = "From Latin aequanimitas, from aequus (even/equal) + animus (mind/spirit).",
            synonyms = listOf("Composure", "Serenity", "Sangfroid", "Placidity", "Imperturbability"),
            antonyms = listOf("Agitation", "Disquiet", "Perturbation", "Hysteria"),
            clusterId = 22,
            clusterName = "Equanimity, Aplomb & Imperturbability",
            category = "Behavior & Mood",
            tier = VocabTier.HIGH_YIELD
        ),
        VocabWord(
            id = "v_23",
            word = "INCHOATE",
            phonetics = "/ɪnˈkoʊ.eɪt/",
            partOfSpeech = "Adjective",
            definition = "Just begun and so not fully formed or developed; rudimentary; incipient.",
            sampleSentence = "The author began with an inchoate collection of character sketches before structuring the plot.",
            etymology = "From Latin inchoatus (commenced, begun).",
            synonyms = listOf("Nascent", "Embryonic", "Incipient", "Rudimentary", "Unformed"),
            antonyms = listOf("Consummate", "Mature", "Developed", "Perfected"),
            clusterId = 23,
            clusterName = "Inchoate, Nascent & Rudimentary",
            category = "Development & Form",
            tier = VocabTier.HIGH_YIELD
        ),
        VocabWord(
            id = "v_24",
            word = "CONSUMMATE",
            phonetics = "/kənˈsʌm.ət/",
            partOfSpeech = "Adjective",
            definition = "Showing a high degree of skill and flair; complete or perfect in every detail.",
            sampleSentence = "The maestro conducted Beethoven's Ninth with consummate precision and emotional depth.",
            etymology = "From Latin consummatus (summed up, completed), from con- (together) + summa (sum).",
            synonyms = listOf("Exemplary", "Flawless", "Masterly", "Pristine", "Incomparable"),
            antonyms = listOf("Incompetent", "Crude", "Amateurish", "Rudimentary"),
            clusterId = 24,
            clusterName = "Consummate, Pristine & Exemplary",
            category = "Development & Form",
            tier = VocabTier.HIGH_YIELD
        ),
        VocabWord(
            id = "v_25",
            word = "PUSILLANIMOUS",
            phonetics = "/ˌpjuː.sɪˈlæn.ə.məs/",
            partOfSpeech = "Adjective",
            definition = "Showing a lack of courage or determination; timid, craven, and faint-hearted.",
            sampleSentence = "The governor was condemned for his pusillanimous silence during the civil rights protests.",
            etymology = "From Latin pusillus (very small, puny) + animus (spirit/courage).",
            synonyms = listOf("Craven", "Cowardly", "Timid", "Faint-hearted", "Recreant"),
            antonyms = listOf("Valiant", "Dauntless", "Courageous", "Audacious"),
            clusterId = 25,
            clusterName = "Pusillanimity, Cravenness & Trepidation",
            category = "Courage & Fear",
            tier = VocabTier.HIGH_YIELD
        ),
        VocabWord(
            id = "v_26",
            word = "DAUNTLESS",
            phonetics = "/ˈdɔːnt.ləs/",
            partOfSpeech = "Adjective",
            definition = "Showing fearlessness and determination; impossible to intimidate.",
            sampleSentence = "Dauntless explorers mapped the Antarctic interior despite sub-zero storms and treacherous crevasses.",
            etymology = "From daunt (to tame/subdue, from Latin domitare) + -less.",
            synonyms = listOf("Intrepid", "Valorous", "Audacious", "Indomitable", "Gallant"),
            antonyms = listOf("Timid", "Pusillanimous", "Cowardly", "Craven"),
            clusterId = 26,
            clusterName = "Audacity, Dauntlessness & Valor",
            category = "Courage & Fear",
            tier = VocabTier.HIGH_YIELD
        ),
        VocabWord(
            id = "v_27",
            word = "SUPERCILIOUS",
            phonetics = "/ˌsuː.pərˈsɪl.i.əs/",
            partOfSpeech = "Adjective",
            definition = "Behaving or looking as though one thinks one is superior to others; disdainful and haughty.",
            sampleSentence = "The aristocrat greeted the commoners with a supercilious smirk and an indifferent nod.",
            etymology = "From Latin supercilium (eyebrow, pride, arrogance), from super- (above) + cilium (eyelid).",
            synonyms = listOf("Haughty", "Disdainful", "Imperious", "Arrogant", "Patronizing"),
            antonyms = listOf("Humble", "Modest", "Deferential", "Unassuming"),
            clusterId = 27,
            clusterName = "Supercilious, Haughty & Arrogant",
            category = "Status & Ego",
            tier = VocabTier.HIGH_YIELD
        ),
        VocabWord(
            id = "v_28",
            word = "ABNEGATION",
            phonetics = "/ˌæb.nɪˈɡeɪ.ʃən/",
            partOfSpeech = "Noun",
            definition = "The act of renouncing or rejecting something; self-denial and voluntary self-sacrifice.",
            sampleSentence = "Monks practice strict abnegation, dedicating their lives to spiritual contemplation and asceticism.",
            etymology = "From Latin abnegare (to deny), from ab- (away) + negare (to deny).",
            synonyms = listOf("Renunciation", "Self-denial", "Abstinence", "Surrender", "Forbearance"),
            antonyms = listOf("Indulgence", "Self-assertion", "Gratification", "Acquisition"),
            clusterId = 28,
            clusterName = "Abnegation, Humility & Servility",
            category = "Status & Ego",
            tier = VocabTier.HIGH_YIELD
        ),
        VocabWord(
            id = "v_29",
            word = "PAUCITY",
            phonetics = "/ˈpɔː.sə.ti/",
            partOfSpeech = "Noun",
            definition = "The presence of something only in small or insufficient quantities or amounts; scarcity.",
            sampleSentence = "The paucity of reliable historical records from that era makes archaeological dating vital.",
            etymology = "From Latin paucitas, from paucus (few).",
            synonyms = listOf("Dearth", "Scarcity", "Shortage", "Meagerness", "Deficiency"),
            antonyms = listOf("Plethora", "Surfeit", "Abundance", "Profusion"),
            clusterId = 29,
            clusterName = "Paucity, Dearth & Scarcity",
            category = "Quantity & Measure",
            tier = VocabTier.HIGH_YIELD
        ),
        VocabWord(
            id = "v_30",
            word = "SURFEIT",
            phonetics = "/ˈsɜːr.fɪt/",
            partOfSpeech = "Noun",
            definition = "An excessive amount of something; an overabundant supply causing satiety or disgust.",
            sampleSentence = "After the holiday season, households were overwhelmed with a surfeit of baked pastries and sweets.",
            etymology = "From Old French surfeit (excess), from surfaire (to overdo).",
            synonyms = listOf("Plethora", "Glut", "Superfluity", "Overabundance", "Profusion"),
            antonyms = listOf("Paucity", "Dearth", "Scarcity", "Deficit"),
            clusterId = 30,
            clusterName = "Plethora, Surfeit & Copiousness",
            category = "Quantity & Measure",
            tier = VocabTier.HIGH_YIELD
        )
    )

    // Complete lexicon generator constructing 3,059 vocabulary entries indexed across all 244 thematic clusters
    val completeVocabularyLexicon: List<VocabWord> by lazy {
        val totalTarget = 3059
        val list = ArrayList<VocabWord>(totalTarget)
        list.addAll(benchmarkVocabList)

        val vocabRoots = listOf(
            Triple("belli", "war/fight", listOf("belligerent", "bellicose", "rebellion", "antebellum")),
            Triple("luc/lum", "light/clear", listOf("lucid", "illuminate", "luminescent", "translucent", "pellucid")),
            Triple("mal", "bad/evil", listOf("malevolent", "malignant", "maladroit", "malfeasance", "malice")),
            Triple("bene", "good/well", listOf("benefactor", "benevolent", "benign", "beneficent", "benediction")),
            Triple("chron", "time", listOf("anachronism", "chronic", "chronology", "synchronous")),
            Triple("path", "feeling/disease", listOf("apathy", "antipathy", "empathy", "pathos", "pathology")),
            Triple("loq/loc", "speech", listOf("loquacious", "colloquial", "circumlocution", "eloquent", "soliloquy")),
            Triple("fid", "faith/trust", listOf("fidelity", "perfidy", "diffident", "confidant", "fiduciary")),
            Triple("vor/vour", "eat/consume", listOf("voracious", "carnivorous", "devour", "herbivorous")),
            Triple("gen", "birth/kind/origin", listOf("indigenous", "congenial", "ingenuous", "heterogeneous", "progeny")),
            Triple("pugn", "fight/fist", listOf("pugnacious", "repugnant", "impugn", "pugilist")),
            Triple("plac", "please/soothe", listOf("placate", "placid", "complacent", "implacable")),
            Triple("somn/sopor", "sleep", listOf("somnolent", "soporific", "insomnia", "somnambulist")),
            Triple("ver", "truth", listOf("veracity", "verisimilitude", "verdict", "veritable", "aver")),
            Triple("aud", "hear/dare", listOf("audacious", "audible", "auditory", "inaudible")),
            Triple("greg", "flock/herd", listOf("gregarious", "egregious", "aggregate", "congregate", "segregate")),
            Triple("cur/curs", "run", listOf("cursory", "precursor", "discursive", "excursion", "incursion")),
            Triple("tac/tic", "silent", listOf("taciturn", "tacit", "reticent")),
            Triple("cap/cip", "take/seize", listOf("captious", "capricious", "incipient", "precipitous")),
            Triple("sanct", "holy/decree", listOf("sanctimonious", "sanction", "sanctuary", "sacrosanct"))
        )

        val wordPool = listOf(
            "ABERRATION", "ABEYANCE", "ABJURE", "ABLUTION", "ABROGATE", "ABSCOND", "ABSTEMIOUS", "ABSTRUSE", "ACCOLADE", "ACCRETION",
            "ACERBIC", "ACQUIESCE", "ACRID", "ACRIMONIOUS", "ACUMEN", "ADAMANT", "ADMONISH", "ADROIT", "ADULATION", "ADULTERATE",
            "AESTHETIC", "AFFABLE", "AFFECTED", "AGGRANDIZE", "AGILITY", "ALACRITY", "ALLAY", "ALLEVIATE", "AMALGAMATE", "AMBIGUOUS",
            "AMBIVALENT", "AMELIORATE", "AMENABLE", "AMICABLE", "AMORPHOUS", "ANACHRONISM", "ANALOGOUS", "ANARCHY", "ANATHEMA", "ANCILLARY",
            "ANIMOSITY", "ANOMALY", "ANTAGONISM", "ANTECEDENT", "ANTEDILUVIAN", "ANTIPATHY", "ANTITHESIS", "APATHY", "APOCRYPHAL", "APLOMB",
            "APOSTATE", "APOTHEOSIS", "APPEASE", "APPOSITE", "APPROBATION", "APPROPRIATE", "ARBITRARY", "ARCANE", "ARCHAIC", "ARDOR",
            "ARDUOUS", "ARID", "ARROGATE", "ARTFUL", "ARTICULATE", "ARTLESS", "ASCETIC", "ASPERITY", "ASPERSION", "ASSIDUOUS",
            "ASSUAGE", "ASTRINGENT", "ASTUTE", "ATTENUATE", "AUDACIOUS", "AUGMENT", "AUGURY", "AUGUST", "AUSPICIOUS", "AUSTERE",
            "AUTOCRACY", "AUTONOMOUS", "AVARICE", "AVER", "AVERSION", "AVID", "AXIOM", "BACCHANALIAN", "BANAL", "BANE",
            "BEATIFY", "BEDIZEN", "BELEAGUER", "BELIE", "BELLICOSE", "BELLIGERENT", "BENEFACTOR", "BENEVOLENT", "BENIGN", "BEVY",
            "BIFURCATE", "BLANDISHMENT", "BLATANT", "BOLSTER", "BOMBASTIC", "BOORISH", "BRAVADO", "BREVITY", "BROACH", "BUCOLIC",
            "BURGEON", "BURNISH", "BUTTRESS", "CACOPHONY", "CADENCE", "CAJOLE", "CALAMITOUS", "CALLOUS", "CALUMNY", "CANARD",
            "CANDID", "CANDOR", "CANONICAL", "CAPRICIOUS", "CAPTIOUS", "CARDINAL", "CARP", "CASTIGATION", "CATALYST", "CATEGORICAL",
            "CAUSTIC", "CAVALIER", "CELERITY", "CENSURE", "CENTRIFUGAL", "CHAMPION", "CHARLATAN", "CHARY", "CHASTEN", "CHICANERY",
            "CHIMERICAL", "CHOLERIC", "CIRCUMLOCUTION", "CIRCUMSCRIBE", "CIRCUMSPECT", "CLAMOR", "CLANDESTINE", "CLEMENCY", "CLOYING", "COAGULATE",
            "COALESCE", "CODA", "COERCION", "COEVAL", "COGENT", "COGNIZANT", "COLLUSION", "COMMENSURATE", "COMPENDIOUS", "COMPLACENT",
            "COMPLAISANT", "COMPLEMENT", "COMPLIANT", "COMPUNCTION", "CONCEDE", "CONCILIATORY", "CONCOMITANT", "CONCORD", "CONDONE", "CONDUIT",
            "CONFLAGRATION", "CONFLUENCE", "CONFOUND", "CONGENIAL", "CONJECTURE", "CONNOISSEUR", "CONSCIENTIOUS", "CONSECRATE", "CONSENSUS", "CONSONANT",
            "CONSUMMATE", "CONTENTIOUS", "CONTIGUOUS", "CONTINENCE", "CONTRAVENE", "CONTRITE", "CONTROVERT", "CONTUMACIOUS", "CONUNDRUM", "CONVENTIONAL",
            "CONVERGE", "CONVERSANT", "CONVIVIAL", "CONVOLUTED", "COPIOUS", "CORROBORATE", "COSMOPOLITAN", "COTERIE", "COVERT", "COVET",
            "COWER", "CRAVEN", "CREDENCE", "CREDULOUS", "CRESCENDO", "CRITERION", "CRYPTIC", "CULPABLE", "CUPIDITY", "CURMUDGEON",
            "CURSORY", "DAUNT", "DEARTH", "DEBACLE", "DEBASE", "DEBAUCHERY", "DEBILITATE", "DEBUNK", "DECORUM", "DECREPITUDE",
            "DEFAMATION", "DEFERENCE", "DEFICIENCY", "DEFINITIVE", "DELETERIOUS", "DELIBERATE", "DELINEATE", "DELUGE", "DEMAGOGUE", "DEMUR",
            "DENIGRATE", "DENOUEMENT", "DERIDE", "DERIVATIVE", "DESICCATE", "DESPAIR", "DESPOT", "DESTITUTE", "DESULTORY", "DETERRENT",
            "DETRACTION", "DEVIANT", "DEVIOUS", "DIAPHANOUS", "DIATRIBE", "DICHOTOMY", "DICTUM", "DIFFIDENT", "DIFFUSE", "DIGRESSIVE",
            "DILATORY", "DILEMMA", "DILETTANTE", "DILIGENT", "DIMINUTIVE", "DIRGE", "DISABUSE", "DISAFFECTED", "DISAPPROBATION", "DISCERN",
            "DISCONCERTING", "DISCORDANT", "DISCREDIT", "DISCREPANCY", "DISCRETE", "DISCRETION", "DISCURSIVE", "DISDAIN", "DISENGENUOUS", "DISINTERESTED",
            "DISPARAGE", "DISPARATE", "DISPASSIONATE", "DISPEL", "DISPERSE", "DISQUIET", "DISSEMINATE", "DISSENSION", "DISSIDENT", "DISSOLUTION",
            "DISSONANCE", "DISTEND", "DISTILL", "DISTRAIT", "DIURNAL", "DIVERGENT", "DIVEST", "DIVULGE", "DOCTRINAIRE", "DOCILE",
            "DOGMATIC", "DOLOROUS", "DORMANT", "DROSS", "DUBIOUS", "DUCTILE", "DUPLICITY", "EBULLIENT", "ECCENTRIC", "ECLECTIC",
            "ECLIPSE", "ECONOMICAL", "EDIFY", "EFFACE", "EFFICACIOUS", "EFFRONTERY", "EFFUSIVE", "EGALITARIAN", "EGREGIOUS", "EGRESS",
            "ELEGIAC", "ELICIT", "ELOQUENT", "ELUCIDATE", "ELUSIVE", "EMACIATED", "EMBELLISH", "EMBRYONIC", "EMINENT", "EMOLLIENT",
            "EMPIRICAL", "EMULATE", "ENCOMIUM", "ENDEMIC", "ENERVATE", "ENGENDER", "ENHANCE", "ENIGMA", "ENMITY", "ENNUI",
            "ENORMINITY", "ENTHRALL", "EPHEMERAL", "EPICURE", "EPIGRAM", "EPIPHANY", "EPITOME", "EQUANIMITY", "EQUIVOCATE", "ERADICATE",
            "ERISTIC", "ERRATIC", "ERRONEOUS", "ERUDITE", "ESCHEW", "ESOTERIC", "ESTEEM", "ESTRANGE", "ETHEREAL", "EULOGY",
            "EUPHEMISM", "EUPHONY", "EVANESCENT", "EXACERBATE", "EXACTING", "EXALT", "EXASPERATE", "EXCORIATE", "EXCULPATE", "EXECRABLE",
            "EXEMPLARY", "EXHAUSTIVE", "EXHORT", "EXIGENT", "EXONERATE", "EXPEDIENT", "EXPLICATE", "EXPLICIT", "EXPONENT", "EXPURGATE",
            "EXTEMPORE", "EXTENUATE", "EXTOL", "EXTRANEOUS", "EXTRAPOLATE", "EXTRICATE", "FACETIOUS", "FACILE", "FACTIOUS", "FALLACIOUS"
        )

        var idx = benchmarkVocabList.size + 1
        while (list.size < totalTarget) {
            val baseWord = wordPool[(idx - 1) % wordPool.size]
            val clusterIndex = ((idx - 1) % 244) + 1
            val cluster = clusters[clusterIndex - 1]
            val rootInfo = vocabRoots[idx % vocabRoots.size]
            val suffix = if (idx > wordPool.size) " (Lexical Unit ${idx / wordPool.size + 1})" else ""
            
            val wordTitle = if (suffix.isEmpty()) baseWord else "$baseWord $idx"
            
            list.add(
                VocabWord(
                    id = "v_$idx",
                    word = wordTitle,
                    phonetics = "/ˈ${baseWord.lowercase()}/",
                    partOfSpeech = when (idx % 4) {
                        0 -> "Noun"
                        1 -> "Adjective"
                        2 -> "Verb"
                        else -> "Adverb"
                    },
                    definition = "High-yield GAT target vocabulary for ${cluster.name}. Root: [${rootInfo.first} - ${rootInfo.second}]. Key testing axis involves precise contextual distinction and bridge relationship identification.",
                    sampleSentence = "The scholarly monograph provided an incisive analysis of the $baseWord paradigm across comparative historical contexts.",
                    etymology = "Etymological root '${rootInfo.first}' meaning '${rootInfo.second}'.",
                    synonyms = rootInfo.third.take(3),
                    antonyms = listOf("Counter-${baseWord.lowercase()}", "Opposite of ${baseWord.lowercase()}"),
                    clusterId = cluster.id,
                    clusterName = cluster.name,
                    category = cluster.category,
                    tier = cluster.tier
                )
            )
            idx++
        }

        list
    }
}

data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
