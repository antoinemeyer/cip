# Correlated Iterators Processor

The goal of this module is to offer a convenient and efficient way to iterate over correlated data contained within multiple sorted iterators.
Each iteration of the resulting sorted stream allows the processing of the correlated data as a single unit of work. 

## Context

It is frequent for banking institutions to make available flat CSV file containing account information such as positions, transactions, or other account information. Those files are usually sorted by account number and can become quite large which makes memory mounting impractical and key grouping inefficient.
Using **Correlated Iterators Processor**, it is possible to open a stream on those different files and process all the data related to an account as a chunk.

## Example

Consider the two following data sets:

  - Data Set 1

| Key  | Value |
| --- | --- |
| B | value11 |
| C	| value12 |
| C	| value13 |
| D	| value14 |
| D	| value15 |
| E	| value16 |

  - Data Set 2

| Key  | Value |
| --- | --- |
| A | value21 |
| A	| value22 |
| C	| value23 |
| C	| value24 |
| C	| value25 |
| D	| value26 |
| G	| value27 |

Opening an iterator on those two streams and running them through CIP using **Key** as the **CorrelationKey** would allow the following processing:
| Key  | Data Set 1 Values | Data Set 2 Values |
| --- | --- |  --- |
| A | | value21, value22 | 
| B | value11 | | 
| C | value12, value13 | value23, value24, value25 | 
| D | value14, value15 | value26 | 
| E | value16 |  | 
| G |  | value27 | 

## Usage

`CorrelatedIterables` contains a collection of convenient iterators to process multiple correlated iterators.

The java classes iterated should contain a field annotated with `@CorrelationKey` that will be used to find the correlations within all the iterators.



