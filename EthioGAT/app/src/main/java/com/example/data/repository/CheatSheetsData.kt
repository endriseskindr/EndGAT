package com.example.data.repository

import com.example.data.models.CheatSheetItem

object CheatSheetsData {
    val items: List<CheatSheetItem> = listOf(
        CheatSheetItem(
            id = "cs_1",
            category = "Quantitative (Number Theory)",
            title = "Divisibility Rules & Prime Factors",
            formulaOrConcept = "Prime Factorization: N = p1^a * p2^b * p3^c\nTotal Factors = (a + 1)(b + 1)(c + 1)",
            explanation = "Any positive integer N can be uniquely decomposed into primes. To count total divisors, add 1 to each exponent in prime factorization and multiply them together.",
            sampleApplication = "How many factors does 360 have? 360 = 2^3 * 3^2 * 5^1. Factors = (3+1)(2+1)(1+1) = 4 * 3 * 2 = 24 factors.",
            tips = listOf(
                "Even factors: exclude 2^0 term in the formula.",
                "Perfect squares always have an ODD number of total factors.",
                "Prime numbers only have 2 factors (1 and itself)."
            )
        ),
        CheatSheetItem(
            id = "cs_2",
            category = "Quantitative (Algebra)",
            title = "Special Algebraic Products & Factoring",
            formulaOrConcept = "(a + b)^2 = a^2 + 2ab + b^2\n(a - b)^2 = a^2 - 2ab + b^2\na^2 - b^2 = (a - b)(a + b)\na^3 - b^3 = (a - b)(a^2 + ab + b^2)",
            explanation = "The Difference of Squares (a^2 - b^2) is the #1 most tested identity in GAT. Look for hidden difference of squares in high-power arithmetic.",
            sampleApplication = "Evaluate (1001^2 - 999^2) instantly without calculating big squares:\n= (1001 - 999)(1001 + 999) = 2 * 2000 = 4,000.",
            tips = listOf(
                "Never calculate large four-digit squares manually on the GAT.",
                "Look for (x + 1/x)^2 = x^2 + 2 + 1/x^2.",
                "Quadratic discriminant: b^2 - 4ac > 0 (2 real roots), = 0 (1 root), < 0 (no real roots)."
            )
        ),
        CheatSheetItem(
            id = "cs_3",
            category = "Quantitative (Geometry)",
            title = "Special Right Triangles & Circle Sectors",
            formulaOrConcept = "30°-60°-90°: 1 : √3 : 2\n45°-45°-90°: 1 : 1 : √2\nArc Length = (θ/360) * 2πr\nSector Area = (θ/360) * πr^2",
            explanation = "Geometry on GAT rewards recognizing special Pythagorean triples (3-4-5, 5-12-13, 8-15-17, 7-24-25) and standard angle ratios.",
            sampleApplication = "If an equilateral triangle has side 8, its altitude bisects it into two 30-60-90 triangles with sides 4, 4√3, 8. Area = (1/2)*8*4√3 = 16√3.",
            tips = listOf(
                "Inscribed angle is always HALF the central angle subtending the same arc.",
                "An angle inscribed in a semicircle is ALWAYS a 90° right angle.",
                "Diagonal of a rectangular prism = √(l^2 + w^2 + h^2)."
            )
        ),
        CheatSheetItem(
            id = "cs_4",
            category = "Quantitative (Combinatorics & Probability)",
            title = "Permutations, Combinations & Overlap",
            formulaOrConcept = "nPr = n! / (n - r)!\nnCr = n! / [r! (n - r)!]\nP(A or B) = P(A) + P(B) - P(A and B)\nP(At least one) = 1 - P(None)",
            explanation = "Use Combinations when order does NOT matter (committees, handshakes, subsets). Use Permutations when order matters (codes, podium finishes, queues).",
            sampleApplication = "Probability of rolling at least one 6 in 3 dice rolls = 1 - P(No 6s in 3 rolls) = 1 - (5/6)^3 = 1 - 125/216 = 91/216.",
            tips = listOf(
                "'At least one' is a massive time-saver cue: always compute 1 - P(none).",
                "Circular seating arrangement of n objects = (n - 1)!.",
                "Overlapping group rule: Total = Group A + Group B - Both + Neither."
            )
        ),
        CheatSheetItem(
            id = "cs_5",
            category = "Verbal (Sentence Completion)",
            title = "Structural Pivot Words & Contrast Clues",
            formulaOrConcept = "Contrast Pivots: Although, However, In contrast, Far from, Despite, Paradoxically, Belie\nSupport Pivots: Furthermore, Because, Consequently, Indeed, Moreover, Given that",
            explanation = "The GAT blank is mathematically determined by the relationship between the clue phrase and the pivot connector. Identify the direction (+ or -) before inspecting options.",
            sampleApplication = "'Far from being [Blank 1], the diplomat was actually notoriously [Blank 2].' The phrase 'Far from being' establishes antonymous contrast between Blank 1 and Blank 2.",
            tips = listOf(
                "Predict the word or its valence (+/ -) in your mind before reading the choices.",
                "Watch out for double negatives (e.g., 'not without hesitation').",
                "Two blanks in Sentence Equivalence must yield coherent, synonymous sentence pairs."
            )
        ),
        CheatSheetItem(
            id = "cs_6",
            category = "Verbal (Critical Reasoning)",
            title = "Argument Core Decomposition & Fallacy Traps",
            formulaOrConcept = "Premise + Unstated Assumption = Conclusion\nAssumption Negation Test: If negating the assumption collapses the conclusion, the assumption is valid.",
            explanation = "To weaken an argument, attack the unstated assumption that connects the premises to the conclusion. Beware of out-of-scope traps.",
            sampleApplication = "If argument says 'Since sales rose after the ad, the ad caused the sales', the unstated assumption is 'No other factor (like a holiday discount) caused the rise'.",
            tips = listOf(
                "Scope Shifts: Beware of premises discussing 'percentage' while conclusions claim 'total raw numbers'.",
                "Correlation ≠ Causation: Reverse causality and confounding third variables are frequent traps.",
                "Extreme words in options (always, never, impossible, exclusively) are red flags unless justified in premises."
            )
        ),
        CheatSheetItem(
            id = "cs_7",
            category = "Analytical Reasoning",
            title = "Linear Ordering & Constraint Grids",
            formulaOrConcept = "1. List Elements (Entities)\n2. Setup Base Positions (1 to N or Days)\n3. Translate Rules into Shorthand Blocks (e.g., [A B], ~[C on 3], D > E)",
            explanation = "In Analytical puzzles, combining 2 or 3 restrictive rules immediately creates deduction blocks that eliminate 80% of possibilities.",
            sampleApplication = "If X must be placed immediately before Y, treat [XY] as a single super-entity. If there are 6 slots, [XY] only has 5 possible placements.",
            tips = listOf(
                "Look for the most restricted entity (the one mentioned in the most rules).",
                "Split boards: If a condition only allows 2 possibilities (e.g. A is in slot 1 OR slot 6), draw both scenarios immediately.",
                "Never guess on 'Must be true' questions without confirming that counter-scenarios are impossible."
            )
        )
    )
}
