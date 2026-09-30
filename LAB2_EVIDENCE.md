# CS4928 Lab 2 Evidence

## Baseline
- Pinned SHA: cf437186b2ae2d9100a187e0df3cce3a163e9b4b
- Upstream `mvn test`: PASS (138 tests, 0 failures)
- CS4928 checker: PASS (5 tests, 0 failures)
- Tag: CS4928_BASELINE

## Problem A
validateName mixes general validation with package-type-specific normalisation. Goal: move the varying name-normalisation algorithm behind a stable policy while preserving every existing result.

## Refactoring A
- Files: added `PackageNamePolicy.java` (interface) and `PackageNamePolicies.java` (four reusable policies + `forType` resolver); simplified `validateName` in `PackageURL.java`.
- Tests after change: `mvn test` PASS (143 tests incl. checker), checker PASS (5/5), `git diff --check` clean.
- Commit: 920cb47fdfe21df36e18968e29dbd617fb720687
- Before → after: `validateName` used to both validate and pick/apply a per-type naming rule; now it only checks the name is non-empty and delegates normalisation to the policy chosen by `forType`, with every type producing the same name as before.

## Problem B
Both `parseQualifiers` methods lowercased qualifier keys with their own copy of the same expression, so the rule "qualifier keys are lowercase" existed in two places. The two methods still have different jobs (the Map path filters empty values and overwrites; the encoded-string path decodes and rejects duplicate keys), so only the shared rule should be extracted, not the methods merged.

## Refactoring B
- Helper: `canonicalizeQualifierKey(String key)` in `PackageURL.java`, used by both `parseQualifiers` methods.
- Tests after change: `mvn test` PASS (143), checker PASS (5/5), `git diff --check` clean.
- Commit: 2497d35c0b836296846bcb635377827e1b8c431c
- Before → after: the key-lowercasing rule now has one source of truth; empty-value filtering and duplicate detection stayed exactly where they were.

## Comparison (Section 6)
- A: The choice of naming rule per package type is now local to `PackageNamePolicies`. The cost is two extra files and a layer of indirection for what was one switch.
- B: "Qualifier keys are lowercase" is defined once. The two input paths deliberately stayed separate because their filtering and duplicate handling differ.
- Why tests aren't enough: passing tests show the tested cases behave the same, not that all behaviour is identical or that the design is easier to change. For example, swapping `StringUtil.toLowerCase` for Java's locale-sensitive `String.toLowerCase()` could change results for non-ASCII names without any current test failing.

## Problem C
Namespace, version and type-constraint rules also vary by package type, but changing them too would make the experiment too large to test and review safely, so it's left for later.

## Final judgement
I would keep both changes in a real pull request. The benefit adding a new package type's naming rule means adding one line in `forType` rather than editing validation logic. The trade-off is for only four rules, there is an extra interface and class add indirection that a reader has to follow.

## Repository
- URL: https://github.com/23365277/cs4928-lab2
- Refactoring B commit SHA: 2497d35c0b836296846bcb635377827e1b8c431c
