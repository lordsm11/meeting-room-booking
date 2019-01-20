#!groovy

// pipeline script

node {
    try {
        def projectGroup = 'devakt'
        def projectName = 'meeting-room-booking'
        def scmUrl = "https://github.com/lordsm11/meeting-room-booking-front.git"


        // Branche à builder
        def branchName = env.BRANCH_NAME

        // Checkout des sources depuis Git
        stage('Checkout') {
            echo ">>> Checkout branch : ${branchName} sur ${scmUrl}"
            git url: "${scmUrl}", branch: "${branchName}"
        }

        // Build des sources
        stage('Build') {
            echo ">>> Build"
            sh "./mvnw --version"
            sh "./mvnw clean install"
        }

        currentBuild.result = 'SUCCESS'
    } catch (any) {
        currentBuild.result = 'FAILURE'
        throw any
    } finally {
        //step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: ['gd-fm-deco@cerqual.fr', emailextrecipients([[$class: 'CulpritsRecipientProvider'], [$class: 'RequesterRecipientProvider']])].join(' ')])
        //step([$class: 'WsCleanup'])
    }
}