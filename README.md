Automation Framework based on Java 8 and TestNG

# Web App: https://thinking-tester-contact-list.herokuapp.com 
(used for UI and API testing , as posted on https://ultimateqa.com/dummy-automation-websites/)

# Install Podman and run Jenkins
## install windows terminal from store(recommended)
## install wsl
## install podman
## podman machine init
## podman machine start
### That VM is where your containers will run (just like Docker Desktop does under the hood).
# pull jenkins image
## podman pull docker.io/jenkins/jenkins:lts
# create volume for jenkins
## podman volume create jenkins_home
## This volume will live inside your Podman VM (Linux environment), not on your Windows filesystem. If you want to bind to a Windows folder, I’ll show you that in the advanced section below.
# run jenkins from podman container
## podman run -d --name jenkins -p 8080:8080 -p 50000:50000 -v jenkins_home:/var/jenkins_home docker.io/jenkins/jenkins:lts
## podman run -d --name jenkins --network host -v jenkins_home:/var/jenkins_home docker.io/jenkins/jenkins:lts
# jenkins running on http://localhost:8080
# unlock jenkins
## podman exec -it jenkins cat /var/jenkins_home/secrets/initialAdminPassword
## paste it in the ui when prompted

#In case you forgot Jenkins credentials
#podman exec -it --user root jenkins bash

mkdir -p /var/jenkins_home/init.groovy.d
chown -R jenkins:jenkins /var/jenkins_home/init.groovy.d

"cat > /var/jenkins_home/init.groovy.d/reset_admin.groovy <<'EOF'
import jenkins.model.*
import hudson.security.*

def instance = Jenkins.getInstance()
def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount("admin","admin")
instance.setSecurityRealm(hudsonRealm)
instance.save()
EOF
"

#expose jenkins with localtunnel
npx localtunnel --port 8080
npx localtunnel --port 8080 --subdomain myjenkins
