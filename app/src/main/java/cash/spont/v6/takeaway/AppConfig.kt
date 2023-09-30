package cash.spont.v6.takeawayrepo.repository

object AppConfig {
    private var auth0ClientId: String = "p1hNabMFJZiQG5hWw5CAQzeYhoPFONSf"
    private var auth0Secret: String = "sAwr-01ZUp3uawuj-c-KKRD0SUPmQWfswDtHchmGQ146xFAHOcZxd90_8j7hNOWj"
    private var auth0Domain: String = "https://spont-staging.eu.auth0.com"
    private var auth0Audience: String = "https://auth0-jwt-authorizer"
    private var dittoAppId: String = "fe60a4fb-95b3-488e-9a98-5e58d2b7b6e2"
    private var dittoProvider: String = "auth0-auth"
    fun  getAccessAuth0ClientId(): String {
        return auth0ClientId
    }

    fun  getAccessAuth0Secret(): String {
        return auth0Secret
    }

    fun  getAccessAuth0Domain(): String {
        return auth0Domain
    }

    fun  getAccessAuth0Audience(): String {
        return auth0Audience
    }

    fun  getAccessDittoAppId(): String {
        return dittoAppId
    }

    fun  getAccessDittoProvider(): String {
        return dittoProvider
    }
}