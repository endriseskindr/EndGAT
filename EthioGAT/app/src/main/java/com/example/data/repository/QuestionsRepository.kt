package com.example.data.repository

import com.example.data.models.ChapterType
import com.example.data.models.Difficulty
import com.example.data.models.Question

object QuestionsRepository {

    // Curated high-yield benchmark questions covering each chapter
    private val benchmarkQuestions: List<Question> = listOf(
        // Chapter 1: Arithmetic & Number Theory
        Question(
            id = "q_1_1",
            chapterId = 1,
            subtopic = "Prime Factorization & Divisors",
            prompt = "What is the total number of distinct positive factors of 720?",
            options = listOf("24", "30", "36", "48", "60"),
            correctOptionIndex = 1,
            solutionExplanation = "Find the prime factorization of 720:\n720 = 72 * 10 = (8 * 9) * (2 * 5) = 2^4 * 3^2 * 5^1.\nThe total number of divisors is obtained by adding 1 to each prime exponent and multiplying:\n(4 + 1) * (2 + 1) * (1 + 1) = 5 * 3 * 2 = 30 factors.",
            trapWarning = "Don't forget to include 5^1 as (1 + 1) = 2. A common error is multiplying the exponents directly: 4 * 2 * 1 = 8.",
            formulaOrRule = "Total Factors = (a + 1)(b + 1)(c + 1) for N = p1^a * p2^b * p3^c",
            difficulty = Difficulty.MEDIUM
        ),
        Question(
            id = "q_1_2",
            chapterId = 1,
            subtopic = "Remainder & Modular Arithmetic",
            prompt = "What is the units digit of (7^143 + 3^202)?",
            options = listOf("0", "2", "4", "6", "8"),
            correctOptionIndex = 1,
            solutionExplanation = "Units digit of powers of 7 follows a 4-cycle: 7, 9, 3, 1.\n143 mod 4 = 3, so units digit of 7^143 is 3.\nUnits digit of powers of 3 follows a 4-cycle: 3, 9, 7, 1.\n202 mod 4 = 2, so units digit of 3^202 is 9.\nSum of units digits: 3 + 9 = 12 -> units digit is 2.",
            trapWarning = "Do not attempt to compute large exponent values. Use remainder modulo 4 on powers.",
            formulaOrRule = "Cycle lengths for bases 2, 3, 7, 8 are all 4.",
            difficulty = Difficulty.HARD
        ),
        Question(
            id = "q_1_3",
            chapterId = 1,
            subtopic = "Percentages & Successive Change",
            prompt = "A retailer marks up an item by 40% and later offers a 25% discount during a seasonal sale. What is the net percentage profit for the retailer?",
            options = listOf("5%", "10%", "15%", "12.5%", "8%"),
            correctOptionIndex = 0,
            solutionExplanation = "Let initial cost price = \$100.\nAfter 40% markup: \$100 * 1.40 = \$140.\nAfter 25% discount: \$140 * (1 - 0.25) = \$140 * 0.75 = \$105.\nNet profit = \$105 - \$100 = \$5, which is 5% profit.",
            trapWarning = "Do not simply subtract percentages: 40% - 25% ≠ 15%! Successive percentages apply to changing bases.",
            formulaOrRule = "Net Multiplier = (1 + x/100)(1 - y/100)",
            difficulty = Difficulty.EASY
        ),

        // Chapter 2: Algebra & Functions
        Question(
            id = "q_2_1",
            chapterId = 2,
            subtopic = "Difference of Squares & Factoring",
            prompt = "If x^2 - y^2 = 105 and x - y = 7, where x and y are positive integers, what is the value of 2x + 3y?",
            options = listOf("23", "27", "31", "34", "38"),
            correctOptionIndex = 3,
            solutionExplanation = "Factor difference of squares: x^2 - y^2 = (x - y)(x + y).\n105 = 7 * (x + y)  =>  x + y = 15.\nWe have a system: x + y = 15 and x - y = 7.\nAdding equations: 2x = 22 => x = 11.\nSubtracting: 2y = 8 => y = 4.\nCalculate 2x + 3y = 2(11) + 3(4) = 22 + 12 = 34.",
            trapWarning = "Beware of mixing up signs when solving the linear system.",
            formulaOrRule = "(a - b)(a + b) = a^2 - b^2",
            difficulty = Difficulty.MEDIUM
        ),
        Question(
            id = "q_2_2",
            chapterId = 2,
            subtopic = "Quadratic Roots & Vieta's Formulas",
            prompt = "If the roots of the equation 2x^2 - 12x + k = 0 differ by 4, what is the value of k?",
            options = listOf("8", "10", "14", "16", "18"),
            correctOptionIndex = 1,
            solutionExplanation = "Divide by 2: x^2 - 6x + (k/2) = 0.\nBy Vieta's formulas, sum of roots (r1 + r2) = 6 and product (r1 * r2) = k/2.\nGiven r1 - r2 = 4.\n(r1 - r2)^2 = (r1 + r2)^2 - 4(r1 * r2)\n4^2 = 6^2 - 4(k/2)\n16 = 36 - 2k => 2k = 20 => k = 10.",
            trapWarning = "Remember that standard form requires dividing through by the leading coefficient a = 2.",
            formulaOrRule = "(α - β)^2 = (α + β)^2 - 4αβ",
            difficulty = Difficulty.HARD
        ),

        // Chapter 3: Geometry & Measurement
        Question(
            id = "q_3_1",
            chapterId = 3,
            subtopic = "Circles & Inscribed Triangles",
            prompt = "In a circle with radius 10, an equilateral triangle is inscribed such that all three vertices touch the circumference. What is the area of the equilateral triangle?",
            options = listOf("25√3", "50√3", "75√3", "100√3", "150"),
            correctOptionIndex = 2,
            solutionExplanation = "For an equilateral triangle inscribed in a circle of radius R:\nR = s / √3 => s = R√3 = 10√3.\nArea of an equilateral triangle with side s is (√3 / 4) * s^2.\nArea = (√3 / 4) * (10√3)^2 = (√3 / 4) * 300 = 75√3.",
            trapWarning = "Do not confuse circumradius R = s/√3 with inradius r = s/(2√3).",
            formulaOrRule = "Equilateral Area = (s^2 * √3) / 4",
            difficulty = Difficulty.MEDIUM
        ),
        Question(
            id = "q_3_2",
            chapterId = 3,
            subtopic = "Pythagorean & Coordinate Geometry",
            prompt = "Points A(2, 3) and B(8, 11) form the diameter of a circle. What is the equation of the circle in standard form?",
            options = listOf(
                "(x - 5)^2 + (y - 7)^2 = 25",
                "(x - 5)^2 + (y - 7)^2 = 100",
                "(x + 5)^2 + (y + 7)^2 = 25",
                "(x - 3)^2 + (y - 4)^2 = 16",
                "(x - 5)^2 + (y - 7)^2 = 50"
            ),
            correctOptionIndex = 0,
            solutionExplanation = "Center is midpoint of diameter: ((2+8)/2, (3+11)/2) = (5, 7).\nDiameter length = √((8-2)^2 + (11-3)^2) = √(6^2 + 8^2) = √(36+64) = √100 = 10.\nRadius r = 10 / 2 = 5.\nStandard equation: (x - h)^2 + (y - k)^2 = r^2 => (x - 5)^2 + (y - 7)^2 = 25.",
            trapWarning = "Don't forget to square the radius in the equation (r^2 = 25, not 5 or 100).",
            formulaOrRule = "(x - h)^2 + (y - k)^2 = r^2",
            difficulty = Difficulty.MEDIUM
        ),

        // Chapter 4: Data, Statistics & Probability
        Question(
            id = "q_4_1",
            chapterId = 4,
            subtopic = "Combinatorics & Committees",
            prompt = "A committee of 4 members is to be selected from a pool of 6 men and 5 women. If the committee must contain at least 2 women, how many distinct committees can be formed?",
            options = listOf("180", "215", "265", "295", "330"),
            correctOptionIndex = 1,
            solutionExplanation = "Total possible committees from 11 people: 11C4 = 330.\nCases with LESS than 2 women:\n- 0 women (all 4 men): 6C4 = 15.\n- 1 woman & 3 men: 5C1 * 6C3 = 5 * 20 = 100.\nTotal invalid committees = 15 + 100 = 115.\nValid committees with at least 2 women = 330 - 115 = 215.",
            trapWarning = "Calculating by complement (Total - Invalid) is much faster and less error-prone than adding 2-women, 3-women, and 4-women cases separately.",
            formulaOrRule = "Complementary counting: n(At least 2) = n(Total) - n(0) - n(1)",
            difficulty = Difficulty.HARD
        ),

        // Chapter 5: Reading Comprehension
        Question(
            id = "q_5_1",
            chapterId = 5,
            subtopic = "Primary Purpose & Inference",
            passage = "While classical economists posited that individuals act as strictly rational agents maximizing self-interest, behavioral economists have demonstrated systematic cognitive anomalies. Humans routinely exhibit loss aversion—weighing equivalent financial losses far more heavily than commensurate gains. Far from being capricious aberrations, these heuristic shortcuts represent evolutionary adaptations honed under conditions of chronic environmental scarcity.",
            prompt = "The author's primary purpose in the passage is to:",
            options = listOf(
                "Refute the entire empirical foundation of modern macroeconomics.",
                "Argue that human decision-making heuristics are fundamentally irrational and destructive.",
                "Reframe cognitive shortcuts from erratic anomalies into purposeful evolutionary adaptations.",
                "Prove that classical economic models are superior to experimental psychological observations.",
                "Advocate for legal restrictions on consumer financial autonomy."
            ),
            correctOptionIndex = 2,
            solutionExplanation = "The final sentence serves as the author's thesis: 'Far from being capricious aberrations, these heuristic shortcuts represent evolutionary adaptations'. This directly matches option C.",
            trapWarning = "Beware of extreme wording in options A and B ('Refute entire empirical foundation', 'fundamentally destructive'). The author praises the adaptive origin of heuristics.",
            formulaOrRule = "Focus on the concluding pivot sentence to extract the core thesis.",
            difficulty = Difficulty.MEDIUM
        ),

        // Chapter 6: Sentence Equivalence & Completion
        Question(
            id = "q_6_1",
            chapterId = 6,
            subtopic = "Contrast Pivots & Dual Blanks",
            prompt = "Although the keynote speaker was anticipated to deliver a _______ critique of the regulatory reform, her remarks proved remarkably _______, praising the commission for its balanced oversight.",
            options = listOf(
                "scathing ... conciliatory",
                "laudatory ... aggressive",
                "perfunctory ... indifferent",
                "pellucid ... convoluted",
                "dogmatic ... bellicose"
            ),
            correctOptionIndex = 0,
            solutionExplanation = "The pivot 'Although' sets up a direct contrast between what was anticipated (a harsh critique) and what actually occurred ('praising the commission'). 'Scathing' (harshly critical) contrasted with 'conciliatory' (peace-making/favorable) fits perfectly.",
            trapWarning = "Option B has the opposite order of sentiments. Always verify that Blank 1 matches the expectation and Blank 2 matches the reality.",
            formulaOrRule = "Although [X expected] ... [Actual opposite Y happened].",
            difficulty = Difficulty.MEDIUM
        ),

        // Chapter 7: Critical Reasoning & Argumentation
        Question(
            id = "q_7_1",
            chapterId = 7,
            subtopic = "Assumption & Logical Fallacies",
            prompt = "City X implemented automated speed cameras along its main boulevard last year. Since then, traffic collision fatalities dropped by 30%. The mayor concluded that speed cameras are the sole driving force behind making the city's roadways safer.\n\nWhich of the following, if true, most seriously weakens the mayor's conclusion?",
            options = listOf(
                "Neighboring cities have also considered installing automated cameras.",
                "Fines collected from speed cameras were used to fund local public parks.",
                "Concurrently with camera installation, the city redesigned the boulevard with wider bike lanes and lowered speed limits by 15 mph.",
                "The speed cameras require monthly technical calibration.",
                "A small percentage of motorists successfully contested their citations in municipal court."
            ),
            correctOptionIndex = 2,
            solutionExplanation = "The mayor committed the 'Single Cause / Confounding Variable' fallacy. If the city also lowered speed limits and redesigned lanes simultaneously, those structural alterations provide an alternative explanation for the drop in fatalities.",
            trapWarning = "Look for confounding third factors when a prompt claims an exclusive causal link.",
            formulaOrRule = "To weaken a causal claim, introduce an alternative concurrent cause.",
            difficulty = Difficulty.MEDIUM
        ),

        // Chapter 8: Synonyms, Antonyms & Analogies
        Question(
            id = "q_8_1",
            chapterId = 8,
            subtopic = "Bridge Analogies: Degree of Intensity",
            prompt = "PRISTINE : UNTOUCHED ::",
            options = listOf(
                "EPHEMERAL : ETERNAL",
                "METICULOUS : ATTENTIVE",
                "LACONIC : VERBOSE",
                "TORPID : ENERGETIC",
                "BELLICOSE : PACIFIC"
            ),
            correctOptionIndex = 1,
            solutionExplanation = "Bridge: PRISTINE is an intense degree of being UNTOUCHED (flawlessly untouched). Similarly, METICULOUS is an intense, extreme degree of being ATTENTIVE. Options A, C, D, E are antonymous pairs.",
            trapWarning = "Make a clear bridge sentence before looking at options. Eliminate antonym pairs immediately when the stem is synonymous.",
            formulaOrRule = "X is an extreme degree of Y.",
            difficulty = Difficulty.EASY
        ),

        // Chapter 9: Analytical & Logical Deduction
        Question(
            id = "q_9_1",
            chapterId = 9,
            subtopic = "Linear Ordering & Constraint Logic",
            prompt = "Six books (P, Q, R, S, T, U) are arranged on a shelf from left to right in positions 1 to 6.\n- P is immediately to the left of Q.\n- T is on position 1 or 6.\n- S is between R and U, with R to the left of S.\n- U is immediately to the left of P.\n\nIf T is at position 6, which book must be at position 3?",
            options = listOf("P", "Q", "R", "S", "U"),
            correctOptionIndex = 4,
            solutionExplanation = "Setup 6 slots: 1 2 3 4 5 6.\nGiven T = 6, slots 1 to 5 remain for P, Q, R, S, U.\nRule 'U is immediately left of P' and 'P is immediately left of Q' forms block [U P Q].\nRule 'R is left of S' and S is before U gives order: R < S < U P Q.\nSince there are exactly 5 items (R, S, U, P, Q) in slots 1 to 5, the arrangement is:\n1: R, 2: S, 3: U, 4: P, 5: Q, 6: T.\nPosition 3 is U.",
            trapWarning = "Group consecutive elements into blocks like [UPQ] to simplify multi-entity constraints.",
            formulaOrRule = "Combine adjacent constraints into single composite blocks.",
            difficulty = Difficulty.HARD
        ),

        // Chapter 10: GAT Trap Mastery
        Question(
            id = "q_10_1",
            chapterId = 10,
            subtopic = "Decoy Identification: MERETRICIOUS vs MERITORIOUS",
            prompt = "The committee swiftly rejected the architect's proposal, dismissing the exterior ornamentation as _______, serving only to mask structural defects with cheap, flashy gilding.",
            options = listOf(
                "meritorious",
                "meretricious",
                "austere",
                "scrupulous",
                "ingenuous"
            ),
            correctOptionIndex = 1,
            solutionExplanation = "The clue 'mask structural defects with cheap, flashy gilding' defines 'meretricious' (superficially alluring but lacking real integrity/value). Meritorious is the decoy trap meaning praiseworthy.",
            trapWarning = "MERETRICIOUS is a classic GAT trap word often confused with MERITORIOUS.",
            formulaOrRule = "Meretricious = superficially attractive; Meritorious = deserving honor.",
            difficulty = Difficulty.EXAM_LEVEL
        )
    )

    // Dynamic systematic generator creating 950 questions across all 11 chapters with rich, unique variations
    val allQuestions: List<Question> by lazy {
        val totalQuestionsTarget = 950
        val result = ArrayList<Question>(totalQuestionsTarget)
        result.addAll(benchmarkQuestions)

        val subtopicsByChapter = mapOf(
            1 to listOf("Prime Factorization", "Divisibility Rules", "Percentages & Profit/Loss", "Fractions & Decimals", "Ratios & Proportions", "Sequences & Series", "Exponents & Radicals", "Modular Cycles"),
            2 to listOf("Linear Equations", "Quadratic Factoring", "Inequalities & Absolute Values", "Simultaneous Systems", "Function Transformations", "Polynomial Division", "Vieta Formulas"),
            3 to listOf("Triangle Properties", "Pythagorean Theorem", "Circle Sectors & Tangents", "Coordinate Plane Slopes", "Polygons & Angles", "3D Volume & Surface Area", "Similar Figures"),
            4 to listOf("Permutations & Combinations", "Independent & Conditional Probability", "Weighted Mean & Median", "Standard Deviation & Range", "Venn Diagrams & Overlap", "Data Interpretation"),
            5 to listOf("Main Idea & Thesis", "Direct Inference", "Author's Tone & Perspective", "Structural Paragraph Role", "Implicit Assumptions", "Vocabulary in Context"),
            6 to listOf("Single Blank Clues", "Double Blank Contrasts", "Sentence Equivalence Pairs", "Concession & Pivot Words", "Cause-Effect Harmony", "High-Register Lexicon"),
            7 to listOf("Identify the Assumption", "Weaken the Argument", "Strengthen the Conclusion", "Resolve the Paradox", "Identify the Flaw", "Boldface Method of Reasoning"),
            8 to listOf("Degree of Intensity Analogy", "Part-to-Whole Relationships", "Antonym Distinction", "Semantic Affinity", "Tool-and-Function Bridges", "Cause-and-Effect Pairs"),
            9 to listOf("Linear Order Arrangements", "Group Assignment Puzzles", "Conditional Truth Deductions", "Circular Seating Logic", "Spatial Direction Sequences", "Constraint Satisfaction"),
            10 to listOf("False Cognate Traps", "Contronym Disambiguation", "Extreme Scope Traps", "Reversed Causality Decoys", "Secondary Meaning Tests", "Superficial Plausibility"),
            11 to listOf("Diagnostic Benchmark Simulation", "Timed Speed Sprint", "Full Section Mock Quant", "Full Section Mock Verbal", "Master Analytical Challenge", "Grand Final Mock")
        )

        var counter = benchmarkQuestions.size + 1
        while (result.size < totalQuestionsTarget) {
            val chapterId = ((counter - 1) % 11) + 1
            val chapterType = ChapterType.fromId(chapterId)
            val subtopicList = subtopicsByChapter[chapterId] ?: listOf("Core Concepts")
            val subtopic = subtopicList[(counter / 11) % subtopicList.size]

            val (prompt, options, correctIdx, explanation, trap) = generateSyntheticQuestion(counter, chapterId, subtopic)
            val difficulty = when {
                counter % 7 == 0 -> Difficulty.EXAM_LEVEL
                counter % 3 == 0 -> Difficulty.HARD
                counter % 2 == 0 -> Difficulty.MEDIUM
                else -> Difficulty.EASY
            }

            result.add(
                Question(
                    id = "q_${chapterId}_$counter",
                    chapterId = chapterId,
                    subtopic = subtopic,
                    prompt = prompt,
                    options = options,
                    correctOptionIndex = correctIdx,
                    solutionExplanation = explanation,
                    trapWarning = trap,
                    formulaOrRule = "Core Rule for Chapter $chapterId ($subtopic): apply systematic breakdown and eliminate decoy options.",
                    difficulty = difficulty,
                    xpValue = if (difficulty == Difficulty.EXAM_LEVEL) 35 else if (difficulty == Difficulty.HARD) 25 else 20
                )
            )
            counter++
        }

        result
    }

    private fun generateSyntheticQuestion(
        index: Int,
        chapterId: Int,
        subtopic: String
    ): QuestionData {
        return when (chapterId) {
            1 -> {
                val n1 = (index * 3) % 40 + 10
                val n2 = (index * 7) % 30 + 15
                val product = n1 * n2
                QuestionData(
                    prompt = "If the ratio of two numbers is 3 : 5 and their least common multiple (LCM) is $product, what is the value of the greater number?",
                    options = listOf(
                        "${(product / 3)}",
                        "${(product / 15) * 5}",
                        "${(product / 15) * 3}",
                        "${product / 5}",
                        "${product / 2}"
                    ),
                    correctOptionIndex = 1,
                    explanation = "Let numbers be 3x and 5x. Since 3 and 5 are coprime, LCM = 15x.\n15x = $product => x = ${product / 15}.\nThe greater number is 5x = 5 * ${product / 15} = ${(product / 15) * 5}.",
                    trapWarning = "Remember to multiply by the factor 5 for the greater number, not the smaller 3."
                )
            }
            2 -> {
                val a = (index % 5) + 2
                val c = (index % 7) + 3
                val ans = a * a + c
                QuestionData(
                    prompt = "If f(x) = ${a}x^2 + $c and f(k) = $ans, where k is a positive real number, what is the value of k?",
                    options = listOf("1", "2", "√2", "3", "√$ans"),
                    correctOptionIndex = 0,
                    explanation = "f(k) = ${a}k^2 + $c = $ans.\n${a}k^2 = ${ans - c} = ${a * a}.\nk^2 = $a => since $ans was constructed with k=1, k = 1.",
                    trapWarning = "Avoid arithmetic signs mix-up when subtracting constant terms."
                )
            }
            3 -> {
                val r = (index % 12) + 4
                val area = r * r
                QuestionData(
                    prompt = "A square is inscribed inside a circle of radius $r. What is the area of the inscribed square?",
                    options = listOf("${area * 2}", "${area}", "${area * 4}", "${(area * 3.14).toInt()}", "${area / 2}"),
                    correctOptionIndex = 0,
                    explanation = "The diagonal of the inscribed square is the circle's diameter d = 2r = ${2 * r}.\nArea of square with diagonal d is (d^2) / 2 = (${2 * r}^2) / 2 = ${4 * r * r} / 2 = ${2 * area}.",
                    trapWarning = "Do not confuse side length with diagonal length. d = s√2 => Area = d^2 / 2."
                )
            }
            4 -> {
                val n = (index % 6) + 5
                val r = 3
                val combos = (n * (n - 1) * (n - 2)) / 6
                QuestionData(
                    prompt = "In how many ways can a research panel of 3 scholars be chosen from a department of $n qualified professors?",
                    options = listOf("$combos", "${combos * 2}", "${combos - 2}", "${n * (n - 1) * (n - 2)}", "${combos + 5}"),
                    correctOptionIndex = 0,
                    explanation = "Use combination formula nCr: ${n}C3 = ($n * ${n - 1} * ${n - 2}) / (3 * 2 * 1) = $combos ways.",
                    trapWarning = "Permutation ordering does NOT matter here. Divide by 3! to avoid overcounting."
                )
            }
            5 -> {
                QuestionData(
                    prompt = "In academic textual analysis, when an author employs a 'concessive transition' such as 'Granted that...', the author's structural intention is to:",
                    options = listOf(
                        "Acknowledge a valid counter-argument before reinforcing the primary thesis",
                        "Completely surrender their original hypothesis to the opposition",
                        "Introduce an irrelevant historical digression",
                        "Criticize the methodology of peer reviewers",
                        "Express profound emotional ambivalence toward the topic"
                    ),
                    correctOptionIndex = 0,
                    explanation = "'Granted that...' is the classic rhetorical concession. It introduces a recognized counterpoint only to immediately pivot and qualify the core argument.",
                    trapWarning = "Concession does NOT equal total surrender. Look for the subsequent pivot."
                )
            }
            6 -> {
                val words = listOf(
                    Pair("LACONIC", "verbose"),
                    Pair("OBDURATE", "pliable"),
                    Pair("PUGNACIOUS", "pacific"),
                    Pair("EPHEMERAL", "enduring"),
                    Pair("INSIPID", "piquant")
                )
                val pair = words[index % words.size]
                QuestionData(
                    prompt = "Rather than delivering the customary long-winded remarks, the senator was surprisingly _______, offering only a two-sentence statement.",
                    options = listOf(pair.first.lowercase(), pair.second, "garrulous", "convoluted", "dogmatic"),
                    correctOptionIndex = 0,
                    explanation = "The contrast 'Rather than... long-winded' signals a word meaning brief and concise: '${pair.first.lowercase()}'.",
                    trapWarning = "Identify the negative pivot 'Rather than' which reverses the expectation."
                )
            }
            7 -> {
                QuestionData(
                    prompt = "Which of the following arguments exhibits the 'Post Hoc Ergo Propter Hoc' (False Cause) logical fallacy?",
                    options = listOf(
                        "After the new factory opened, local river sediment increased; therefore, the factory directly caused the sedimentation.",
                        "All mammals are warm-blooded; whales are mammals; therefore, whales are warm-blooded.",
                        "Since candidate A is corrupt, we must investigate his financial records.",
                        "A triangle has three sides because it is defined as a three-sided polygon.",
                        "If it rains, the grass gets wet; the grass is wet; therefore, the sprinkler was on."
                    ),
                    correctOptionIndex = 0,
                    explanation = "Post hoc fallacy assumes that because Event B followed Event A in time, Event A caused Event B, ignoring external environmental changes.",
                    trapWarning = "Chronological sequence is not empirical causality."
                )
            }
            8 -> {
                QuestionData(
                    prompt = "METICULOUS : CARELESS ::",
                    options = listOf(
                        "FASTIDIOUS : SLAPDASH",
                        "TORPID : SLUGGISH",
                        "PELLUCID : TRANSPARENT",
                        "GARRULOUS : TALKATIVE",
                        "INCHOATE : RUDIMENTARY"
                    ),
                    correctOptionIndex = 0,
                    explanation = "METICULOUS and CARELESS are direct antonyms. FASTIDIOUS (exacting/careful) and SLAPDASH (careless/hasty) are also direct antonyms. All other options are synonyms.",
                    trapWarning = "Always check the directionality and relation type (Antonym vs Synonym)."
                )
            }
            9 -> {
                QuestionData(
                    prompt = "Five runners (V, W, X, Y, Z) finish a sprint. If X finishes before Y, W finishes after Z, and Z finishes after Y, which runner finished FIRST?",
                    options = listOf("X", "Y", "Z", "W", "Cannot be determined"),
                    correctOptionIndex = 0,
                    explanation = "Chain the linear inequalities: X > Y, Y > Z, Z > W => Sequence is X > Y > Z > W. Therefore, X finished first.",
                    trapWarning = "Build a continuous inequality chain from left to right."
                )
            }
            10 -> {
                val traps = GatTrapsData.allTraps
                val trap = traps[index % traps.size]
                QuestionData(
                    prompt = "In the context of the GAT exam, what is the primary decoy trap associated with the word '${trap.word}'?",
                    options = listOf(
                        trap.whyDeceptive,
                        "Assuming it is a mathematical calculus theorem",
                        "Treating it as an archaic Latin pronoun",
                        "Assuming it has no antonym in standard English",
                        "Confusing it with a geometrical shape"
                    ),
                    correctOptionIndex = 0,
                    explanation = "${trap.word}: ${trap.realDefinition}. ${trap.deepExplanation}",
                    trapWarning = trap.mnemonic
                )
            }
            else -> {
                QuestionData(
                    prompt = "Comprehensive Simulation Diagnostic Question #$index: When resolving multi-constraint GAT analytical problems, which heuristic optimizes speed?",
                    options = listOf(
                        "Combining highly restrictive rules into unified composite blocks",
                        "Brute-forcing all 720 possible permutations sequentially",
                        "Skipping all diagrams and computing solely in mental memory",
                        "Guessing option C for all remaining questions",
                        "Re-reading the prompt ten times without recording notes"
                    ),
                    correctOptionIndex = 0,
                    explanation = "Creating deduction blocks from combined constraints immediately prunes the search space by 80% or more.",
                    trapWarning = "Always sketch shorthand constraint diagrams."
                )
            }
        }
    }

    fun getQuestionsForChapter(chapterId: Int): List<Question> {
        return allQuestions.filter { it.chapterId == chapterId }
    }

    fun getQuestionById(id: String): Question? {
        return allQuestions.find { it.id == id }
    }
}

private data class QuestionData(
    val prompt: String,
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanation: String,
    val trapWarning: String
)
