.PHONY: test
test:
	lein test

test-refresh:
	lein test-refresh

help:
	lein run -- --help

dataflow:
	lein run -- --runner=DataflowRunner --project=urban-dictionary-fastly --tempLocation=gs://urban-hello-world/tmp

local:
	lein run
