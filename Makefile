
DOCKER_HOSTNAME := ""
GOOGLE_PROJECT_ID := $(gcloud config get core/project)
GOOGLE_AR_REPOS_PATH := descriptive-analytics/dev
DOCKER_IMAGE_TAG := descr-analyt:$((git rev-parse HEAD))

build:
	docker build -e OCP_APIM_SUBSCRIPTION_KEY -t $(DOCKER_IMAGE_TAG) .

push: build
	docker push $(DOCKER_IMAGE_TAG)
