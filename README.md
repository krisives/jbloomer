
[![Build Status](https://travis-ci.org/krisives/jbloomer.svg?branch=master)](https://travis-ci.org/krisives/jbloomer)

# jbloomer

A simple Java library for [Bloom Filters](https://en.wikipedia.org/wiki/Bloom_filter).

## License

See the LICENSE.md for a copy of the BSD 2-clause license.

## Download

Grab the Jar from releases and add it to your classpath or buildpath.

## Usage

Create a bloom filter by passing how many items you expect to be
in the filter and a expected tolerance of false positives to occur.

```
BloomFilter filter = new BloomFilter(1000, 0.01);
```

You can add items to the filter by:

```
int ip = addr.getIpAddress();
filter.add(ip);
```

Later you can check if a key has been added to the filter:

```
if (filter.contains(ip)) {
	throw new Exception("IP is blacklisted");
}
```

## Bugs

Bugs are tracked on GitHub at https://github.com/krisives/jbloomer/issues

## Contributing

If you want to improve jbloomer fork [krisives/jbloomer](https://github.com/krisives/jbloomer)
and make your changes then submit a [pull request](https://github.com/krisives/jbloomer/pulls).

