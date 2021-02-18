# Correlated Iterators Processor

The goal of this module is to offer a convenient and efficient way to process multiple sorted iterators containing correlated data.

It is for example frequent for banking structures to offer flat CSV files dump of positions, transactions, or other account information sorted by account number.
Using CIP, it is possible to open a stream on those different files and process all the data related to an account as a chunk.

The type of the iterators should contain a field annotated with `@CorrelationKey` that will be used to find the correlations within all the iterators.

See `CorrelatedIterables` to get started.
