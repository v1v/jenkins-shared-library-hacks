.DEFAULT_GOAL := start

.PHONY: build
build:  ## Build the docker image with all the plugins
	@env docker-compose --file docker-compose.yml build

.PHONY: start
start:  ## Start your local jenkins instance
	@env docker-compose --file docker-compose.yml up --detach --no-recreate

.PHONY: stop
stop:  ## Stop your local jenkins instance
	@env docker-compose --file docker-compose.yml stop

.PHONY: start-local-worker
start-local-worker:  ## Start the local worker
	[ ! -e agent.jar ] && wget --quiet -O agent.jar http://localhost:28080/jnlpJars/agent.jar || true
	{ nohup java -jar agent.jar -jnlpUrl http://localhost:28080/computer/local/slave-agent.jnlp > local.log 2>&1 & echo $$! > local.pid; }

.PHONY: stop-local-worker
stop-local-worker:  ## Stop the local worker
	[ -e local.pid ] && ( kill -9 `cat local.pid` ; rm local.pid ) || true

.PHONY: start-linux-worker
start-linux-worker:  ## Start the local linux worker
	VAGRANT_CWD=workers/linux/ vagrant up --provision

.PHONY: stop-linux-worker
stop-linux-worker:  ## Stop the local linux worker
	VAGRANT_CWD=workers/linux/ vagrant halt

.PHONY: help
help:  ## Show this help message
	@awk 'BEGIN {FS = ":.*##"; printf "Usage: make \033[36m<target>\033[0m\n\nTargets:\n"} /^[a-zA-Z_-]+:.*?##/ { printf "  \033[36m%-10s\033[0m %s\n", $$1, $$2 }' $(MAKEFILE_LIST)
