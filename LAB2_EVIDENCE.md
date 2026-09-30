# CS4928 Lab 2 Evidence

## Baseline
- Pinned SHA: cf437186b2ae2d9100a187e0df3cce3a163e9b4b
- Upstream `mvn test`: PASS (138 tests, 0 failures)
- CS4928 checker: PASS (5 tests, 0 failures)
- Tag: CS4928_BASELINE

## Problem A
validateName mixes general validation with package-type-specific normalisation. Goal: move the varying name-normalisation algorithm behind a stable policy while preserving every existing result.
