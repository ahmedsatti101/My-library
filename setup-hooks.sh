#!/bin/sh

# Copy hooks to the .git/hooks directory
cp hooks/* .git/hooks/
chmod +x .git/hooks/*
