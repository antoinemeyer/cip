# Correlated Iterators Processor

The goal of this module is to offer a convenient and efficient way to process multiple iterators containing correlated data.

It is for example frequent for banking structures to offer flat CSV files dump of positions, transactions, or other account information.
Using CIP, it is possible to open a stream on those different files and process all the data related to an account as a chunk.

See CorrelatedIterables to get started.