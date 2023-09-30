package cash.spont.v6.takeaway.ui.models

import live.ditto.DittoDocument

data class User(
    var adminOf: List<Any?>,
    var companies: List<Any?>,
    var createdOn: String,
    var currentCompany: String?,
    var email: String?,
    var firstName: String?,
    var _id: List<Any?>,
    var image: String?,
    var language: String?,
    var lastName: String?,
    var modifiedOn: Int?,
    var name: String?,
    var phoneNo: String?,
    var pin: String?,
    var userOf: List<Any?>?
) {
    constructor(document: DittoDocument) : this(
        document["admin"].listValue,
        document["companies"].listValue,
        document["createdOn"].stringValue,
        document["currentCompany"].stringValue,
        document["email"].stringValue,
        document["firstName"].stringValue,
        document["_id"].listValue,
        document["image"].stringValue,
        document["language"].stringValue,
        document["lastName"].stringValue,
        document["modifiedOn"].intValue,
        document["name"].stringValue,
        document["phoneNo"].stringValue,
        document["pin"].stringValue,
        document["userOf"].listValue
    )
}
