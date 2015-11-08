#!/bin/sh

fail() {
  echo "Build failed."
  exit 1
}

mkdir -p build || fail
echo "Compiling..." && node_modules/.bin/babel --presets es2015,react src/ --out-dir lib/ || fail
echo "Browserifying..." && node_modules/.bin/browserify -o build/bundle.js lib/index.js || fail
echo "Copying..." && cp -r static/* build/ || fail
