# zeek [![Build Status](https://secure.travis-ci.org/ssurgnier/zeek.png?branch=master)](http://travis-ci.org/ssurgnier/zeek)

A simple zookeeper cli.

## Create a configuration file
```
mkdir -p resources && echo {:local \"localhost:2181\"} > resources/config.clj
```

## Usage
```
lein run resources/config.clj local

hostname@local:/ $ ls
(znode1 znode2 znode3)
hostname@local:/ $ cd znode1
localhost@local:/znode1 $ get
#<byte[] [B@727f3b8a>
hostname@local:/znode1 $ cd ..
localhost@local:/ $ rm znode1
Removed /znode1
hostname@local:/ $
```

## License

Copyright © 2013 Steven Surgnier

Distributed under the Eclipse Public License, the same as Clojure.
