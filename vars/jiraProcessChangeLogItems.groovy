def call(Map config=[:]) {
  def changeLogItems = currentBuild.getBuildCauses()[0]?.event?.changelog?.items
  if(0 == changeLogItems.size()) {
    println 'no items found in changelog'
    return
  }
  changeLogItems.each {
    println it.fromString + '; ' + it.toString
    def fromTo = [it.fromString,it.toString]
    switch(fromTo) {
      case {it == ["Backlog","Selected for Development"]}
        break;
      case {it == ["Selected for Development","In Progress"]}
        def component = currentBuild.getBuildCauses()[0]?.event?.issue?.fields?.components[0]?.name
        def fixVersion = currentBuild.getBuildCauses()[0]?.event?.issue?.fields?.fixVersions[0]?.name
        def m = [
          event: "deploy-to-stage",
          component: component,
          version: fixVersion
        ]
        println m.inspect()
        //publishEvent(jsonEvent('{"foo":"bar"}'))
        break;
    }
  }
}