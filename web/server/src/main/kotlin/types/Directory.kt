@file:JsQualifier("admin_directory_v1")
@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION")

package admin_directory_v1

import google.APIRequestContext
import google.BodyResponseCallback
import google.GlobalOptions
import google.GoogleConfigurable
import google.MethodOptions
import kotlin.js.Promise

external interface Options : GlobalOptions {
    var version: String /* 'directory_v1' */
}

external interface StandardParameters {
    var alt: String?
        get() = definedExternally
        set(value) = definedExternally
    var fields: String?
        get() = definedExternally
        set(value) = definedExternally
    var key: String?
        get() = definedExternally
        set(value) = definedExternally
    var oauth_token: String?
        get() = definedExternally
        set(value) = definedExternally
    var prettyPrint: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var quotaUser: String?
        get() = definedExternally
        set(value) = definedExternally
    var userIp: String?
        get() = definedExternally
        set(value) = definedExternally
}

open external class Admin(options: GlobalOptions, google: GoogleConfigurable? = definedExternally) {
    open var context: APIRequestContext
    open var asps: `Resource$Asps`
    open var channels: `Resource$Channels`
    open var chromeosdevices: `Resource$Chromeosdevices`
    open var customers: `Resource$Customers`
    open var domainAliases: `Resource$Domainaliases`
    open var domains: `Resource$Domains`
    open var groups: `Resource$Groups`
    open var members: `Resource$Members`
    open var mobiledevices: `Resource$Mobiledevices`
    open var notifications: `Resource$Notifications`
    open var orgunits: `Resource$Orgunits`
    open var privileges: `Resource$Privileges`
    open var resolvedAppAccessSettings: `Resource$Resolvedappaccesssettings`
    open var resources: `Resource$Resources`
    open var roleAssignments: `Resource$Roleassignments`
    open var roles: `Resource$Roles`
    open var schemas: `Resource$Schemas`
    open var tokens: `Resource$Tokens`
    open var users: `Resource$Users`
    open var verificationCodes: `Resource$Verificationcodes`
}

external interface `Schema$Base`<T> {
    var data: T
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Alias` {
    var alias: String?
        get() = definedExternally
        set(value) = definedExternally
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var id: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var primaryEmail: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Aliases` {
    var aliases: Array<Any>?
        get() = definedExternally
        set(value) = definedExternally
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$AppAccessCollections` {
    var blockedApiAccessBuckets: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var enforceSettingsForAndroidDrive: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var errorMessage: String?
        get() = definedExternally
        set(value) = definedExternally
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var resourceId: String?
        get() = definedExternally
        set(value) = definedExternally
    var resourceName: String?
        get() = definedExternally
        set(value) = definedExternally
    var trustDomainOwnedApps: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Asp` {
    var codeId: Number?
        get() = definedExternally
        set(value) = definedExternally
    var creationTime: String?
        get() = definedExternally
        set(value) = definedExternally
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var lastTimeUsed: String?
        get() = definedExternally
        set(value) = definedExternally
    var name: String?
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Asps` {
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var items: Array<`Schema$Asp`>?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Building` {
    var address: `Schema$BuildingAddress`?
        get() = definedExternally
        set(value) = definedExternally
    var buildingId: String?
        get() = definedExternally
        set(value) = definedExternally
    var buildingName: String?
        get() = definedExternally
        set(value) = definedExternally
    var coordinates: `Schema$BuildingCoordinates`?
        get() = definedExternally
        set(value) = definedExternally
    var description: String?
        get() = definedExternally
        set(value) = definedExternally
    var etags: String?
        get() = definedExternally
        set(value) = definedExternally
    var floorNames: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$BuildingAddress` {
    var addressLines: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var administrativeArea: String?
        get() = definedExternally
        set(value) = definedExternally
    var languageCode: String?
        get() = definedExternally
        set(value) = definedExternally
    var locality: String?
        get() = definedExternally
        set(value) = definedExternally
    var postalCode: String?
        get() = definedExternally
        set(value) = definedExternally
    var regionCode: String?
        get() = definedExternally
        set(value) = definedExternally
    var sublocality: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$BuildingCoordinates` {
    var latitude: Number?
        get() = definedExternally
        set(value) = definedExternally
    var longitude: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Buildings` {
    var buildings: Array<`Schema$Building`>?
        get() = definedExternally
        set(value) = definedExternally
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var nextPageToken: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$CalendarResource` {
    var buildingId: String?
        get() = definedExternally
        set(value) = definedExternally
    var capacity: Number?
        get() = definedExternally
        set(value) = definedExternally
    var etags: String?
        get() = definedExternally
        set(value) = definedExternally
    var featureInstances: Any?
        get() = definedExternally
        set(value) = definedExternally
    var floorName: String?
        get() = definedExternally
        set(value) = definedExternally
    var floorSection: String?
        get() = definedExternally
        set(value) = definedExternally
    var generatedResourceName: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var resourceCategory: String?
        get() = definedExternally
        set(value) = definedExternally
    var resourceDescription: String?
        get() = definedExternally
        set(value) = definedExternally
    var resourceEmail: String?
        get() = definedExternally
        set(value) = definedExternally
    var resourceId: String?
        get() = definedExternally
        set(value) = definedExternally
    var resourceName: String?
        get() = definedExternally
        set(value) = definedExternally
    var resourceType: String?
        get() = definedExternally
        set(value) = definedExternally
    var userVisibleDescription: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$CalendarResources` {
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var items: Array<`Schema$CalendarResource`>?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var nextPageToken: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Channel` {
    var address: String?
        get() = definedExternally
        set(value) = definedExternally
    var expiration: String?
        get() = definedExternally
        set(value) = definedExternally
    var id: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var params: dynamic /* `T$0` | Nothing? */
        get() = definedExternally
        set(value) = definedExternally
    var payload: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var resourceId: String?
        get() = definedExternally
        set(value) = definedExternally
    var resourceUri: String?
        get() = definedExternally
        set(value) = definedExternally
    var token: String?
        get() = definedExternally
        set(value) = definedExternally
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$1` {
    var activeTime: Number?
        get() = definedExternally
        set(value) = definedExternally
    var date: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$2` {
    var label: String?
        get() = definedExternally
        set(value) = definedExternally
    var temperature: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$3` {
    var cpuTemperatureInfo: Array<`T$2`>?
        get() = definedExternally
        set(value) = definedExternally
    var cpuUtilizationPercentageInfo: Array<Number>?
        get() = definedExternally
        set(value) = definedExternally
    var reportTime: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$4` {
    var createTime: String?
        get() = definedExternally
        set(value) = definedExternally
    var downloadUrl: String?
        get() = definedExternally
        set(value) = definedExternally
    var name: String?
        get() = definedExternally
        set(value) = definedExternally
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$5` {
    var storageFree: String?
        get() = definedExternally
        set(value) = definedExternally
    var storageTotal: String?
        get() = definedExternally
        set(value) = definedExternally
    var volumeId: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$6` {
    var volumeInfo: Array<`T$5`>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$7` {
    var email: String?
        get() = definedExternally
        set(value) = definedExternally
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$8` {
    var reportTime: String?
        get() = definedExternally
        set(value) = definedExternally
    var systemRamFreeInfo: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$9` {
    var family: String?
        get() = definedExternally
        set(value) = definedExternally
    var firmwareVersion: String?
        get() = definedExternally
        set(value) = definedExternally
    var manufacturer: String?
        get() = definedExternally
        set(value) = definedExternally
    var specLevel: String?
        get() = definedExternally
        set(value) = definedExternally
    var tpmModel: String?
        get() = definedExternally
        set(value) = definedExternally
    var vendorSpecific: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$ChromeOsDevice` {
    var activeTimeRanges: Array<`T$1`>?
        get() = definedExternally
        set(value) = definedExternally
    var annotatedAssetId: String?
        get() = definedExternally
        set(value) = definedExternally
    var annotatedLocation: String?
        get() = definedExternally
        set(value) = definedExternally
    var annotatedUser: String?
        get() = definedExternally
        set(value) = definedExternally
    var autoUpdateExpiration: String?
        get() = definedExternally
        set(value) = definedExternally
    var bootMode: String?
        get() = definedExternally
        set(value) = definedExternally
    var cpuStatusReports: Array<`T$3`>?
        get() = definedExternally
        set(value) = definedExternally
    var deviceFiles: Array<`T$4`>?
        get() = definedExternally
        set(value) = definedExternally
    var deviceId: String?
        get() = definedExternally
        set(value) = definedExternally
    var diskVolumeReports: Array<`T$6`>?
        get() = definedExternally
        set(value) = definedExternally
    var dockMacAddress: String?
        get() = definedExternally
        set(value) = definedExternally
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var ethernetMacAddress: String?
        get() = definedExternally
        set(value) = definedExternally
    var ethernetMacAddress0: String?
        get() = definedExternally
        set(value) = definedExternally
    var firmwareVersion: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var lastEnrollmentTime: String?
        get() = definedExternally
        set(value) = definedExternally
    var lastSync: String?
        get() = definedExternally
        set(value) = definedExternally
    var macAddress: String?
        get() = definedExternally
        set(value) = definedExternally
    var manufactureDate: String?
        get() = definedExternally
        set(value) = definedExternally
    var meid: String?
        get() = definedExternally
        set(value) = definedExternally
    var model: String?
        get() = definedExternally
        set(value) = definedExternally
    var notes: String?
        get() = definedExternally
        set(value) = definedExternally
    var orderNumber: String?
        get() = definedExternally
        set(value) = definedExternally
    var orgUnitPath: String?
        get() = definedExternally
        set(value) = definedExternally
    var osVersion: String?
        get() = definedExternally
        set(value) = definedExternally
    var platformVersion: String?
        get() = definedExternally
        set(value) = definedExternally
    var recentUsers: Array<`T$7`>?
        get() = definedExternally
        set(value) = definedExternally
    var serialNumber: String?
        get() = definedExternally
        set(value) = definedExternally
    var status: String?
        get() = definedExternally
        set(value) = definedExternally
    var supportEndDate: String?
        get() = definedExternally
        set(value) = definedExternally
    var systemRamFreeReports: Array<`T$8`>?
        get() = definedExternally
        set(value) = definedExternally
    var systemRamTotal: String?
        get() = definedExternally
        set(value) = definedExternally
    var tpmVersionInfo: dynamic /* `T$9` | Nothing? */
        get() = definedExternally
        set(value) = definedExternally
    var willAutoRenew: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$ChromeOsDeviceAction` {
    var action: String?
        get() = definedExternally
        set(value) = definedExternally
    var deprovisionReason: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$ChromeOsDevices` {
    var chromeosdevices: Array<`Schema$ChromeOsDevice`>?
        get() = definedExternally
        set(value) = definedExternally
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var nextPageToken: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$ChromeOsMoveDevicesToOu` {
    var deviceIds: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Customer` {
    var alternateEmail: String?
        get() = definedExternally
        set(value) = definedExternally
    var customerCreationTime: String?
        get() = definedExternally
        set(value) = definedExternally
    var customerDomain: String?
        get() = definedExternally
        set(value) = definedExternally
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var id: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var language: String?
        get() = definedExternally
        set(value) = definedExternally
    var phoneNumber: String?
        get() = definedExternally
        set(value) = definedExternally
    var postalAddress: `Schema$CustomerPostalAddress`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$CustomerPostalAddress` {
    var addressLine1: String?
        get() = definedExternally
        set(value) = definedExternally
    var addressLine2: String?
        get() = definedExternally
        set(value) = definedExternally
    var addressLine3: String?
        get() = definedExternally
        set(value) = definedExternally
    var contactName: String?
        get() = definedExternally
        set(value) = definedExternally
    var countryCode: String?
        get() = definedExternally
        set(value) = definedExternally
    var locality: String?
        get() = definedExternally
        set(value) = definedExternally
    var organizationName: String?
        get() = definedExternally
        set(value) = definedExternally
    var postalCode: String?
        get() = definedExternally
        set(value) = definedExternally
    var region: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$DomainAlias` {
    var creationTime: String?
        get() = definedExternally
        set(value) = definedExternally
    var domainAliasName: String?
        get() = definedExternally
        set(value) = definedExternally
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var parentDomainName: String?
        get() = definedExternally
        set(value) = definedExternally
    var verified: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$DomainAliases` {
    var domainAliases: Array<`Schema$DomainAlias`>?
        get() = definedExternally
        set(value) = definedExternally
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Domains` {
    var creationTime: String?
        get() = definedExternally
        set(value) = definedExternally
    var domainAliases: Array<`Schema$DomainAlias`>?
        get() = definedExternally
        set(value) = definedExternally
    var domainName: String?
        get() = definedExternally
        set(value) = definedExternally
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var isPrimary: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var verified: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Domains2` {
    var domains: Array<`Schema$Domains`>?
        get() = definedExternally
        set(value) = definedExternally
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Feature` {
    var etags: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var name: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$FeatureInstance` {
    var feature: `Schema$Feature`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$FeatureRename` {
    var newName: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Features` {
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var features: Array<`Schema$Feature`>?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var nextPageToken: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Group` {
    var adminCreated: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var aliases: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var description: String?
        get() = definedExternally
        set(value) = definedExternally
    var directMembersCount: String?
        get() = definedExternally
        set(value) = definedExternally
    var email: String?
        get() = definedExternally
        set(value) = definedExternally
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var id: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var name: String?
        get() = definedExternally
        set(value) = definedExternally
    var nonEditableAliases: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Groups` {
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var groups: Array<`Schema$Group`>?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var nextPageToken: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Member` {
    var delivery_settings: String?
        get() = definedExternally
        set(value) = definedExternally
    var email: String?
        get() = definedExternally
        set(value) = definedExternally
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var id: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var role: String?
        get() = definedExternally
        set(value) = definedExternally
    var status: String?
        get() = definedExternally
        set(value) = definedExternally
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Members` {
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var members: Array<`Schema$Member`>?
        get() = definedExternally
        set(value) = definedExternally
    var nextPageToken: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$MembersHasMember` {
    var isMember: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$10` {
    var displayName: String?
        get() = definedExternally
        set(value) = definedExternally
    var packageName: String?
        get() = definedExternally
        set(value) = definedExternally
    var permission: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var versionCode: Number?
        get() = definedExternally
        set(value) = definedExternally
    var versionName: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$MobileDevice` {
    var adbStatus: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var applications: Array<`T$10`>?
        get() = definedExternally
        set(value) = definedExternally
    var basebandVersion: String?
        get() = definedExternally
        set(value) = definedExternally
    var bootloaderVersion: String?
        get() = definedExternally
        set(value) = definedExternally
    var brand: String?
        get() = definedExternally
        set(value) = definedExternally
    var buildNumber: String?
        get() = definedExternally
        set(value) = definedExternally
    var defaultLanguage: String?
        get() = definedExternally
        set(value) = definedExternally
    var developerOptionsStatus: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var deviceCompromisedStatus: String?
        get() = definedExternally
        set(value) = definedExternally
    var deviceId: String?
        get() = definedExternally
        set(value) = definedExternally
    var devicePasswordStatus: String?
        get() = definedExternally
        set(value) = definedExternally
    var email: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var encryptionStatus: String?
        get() = definedExternally
        set(value) = definedExternally
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var firstSync: String?
        get() = definedExternally
        set(value) = definedExternally
    var hardware: String?
        get() = definedExternally
        set(value) = definedExternally
    var hardwareId: String?
        get() = definedExternally
        set(value) = definedExternally
    var imei: String?
        get() = definedExternally
        set(value) = definedExternally
    var kernelVersion: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var lastSync: String?
        get() = definedExternally
        set(value) = definedExternally
    var managedAccountIsOnOwnerProfile: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var manufacturer: String?
        get() = definedExternally
        set(value) = definedExternally
    var meid: String?
        get() = definedExternally
        set(value) = definedExternally
    var model: String?
        get() = definedExternally
        set(value) = definedExternally
    var name: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var networkOperator: String?
        get() = definedExternally
        set(value) = definedExternally
    var os: String?
        get() = definedExternally
        set(value) = definedExternally
    var otherAccountsInfo: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var privilege: String?
        get() = definedExternally
        set(value) = definedExternally
    var releaseVersion: String?
        get() = definedExternally
        set(value) = definedExternally
    var resourceId: String?
        get() = definedExternally
        set(value) = definedExternally
    var securityPatchLevel: String?
        get() = definedExternally
        set(value) = definedExternally
    var serialNumber: String?
        get() = definedExternally
        set(value) = definedExternally
    var status: String?
        get() = definedExternally
        set(value) = definedExternally
    var supportsWorkProfile: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
    var unknownSourcesStatus: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var userAgent: String?
        get() = definedExternally
        set(value) = definedExternally
    var wifiMacAddress: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$MobileDeviceAction` {
    var action: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$MobileDevices` {
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var mobiledevices: Array<`Schema$MobileDevice`>?
        get() = definedExternally
        set(value) = definedExternally
    var nextPageToken: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Notification` {
    var body: String?
        get() = definedExternally
        set(value) = definedExternally
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var fromAddress: String?
        get() = definedExternally
        set(value) = definedExternally
    var isUnread: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var notificationId: String?
        get() = definedExternally
        set(value) = definedExternally
    var sendTime: String?
        get() = definedExternally
        set(value) = definedExternally
    var subject: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Notifications` {
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var items: Array<`Schema$Notification`>?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var nextPageToken: String?
        get() = definedExternally
        set(value) = definedExternally
    var unreadNotificationsCount: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$OrgUnit` {
    var blockInheritance: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var description: String?
        get() = definedExternally
        set(value) = definedExternally
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var name: String?
        get() = definedExternally
        set(value) = definedExternally
    var orgUnitId: String?
        get() = definedExternally
        set(value) = definedExternally
    var orgUnitPath: String?
        get() = definedExternally
        set(value) = definedExternally
    var parentOrgUnitId: String?
        get() = definedExternally
        set(value) = definedExternally
    var parentOrgUnitPath: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$OrgUnits` {
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var organizationUnits: Array<`Schema$OrgUnit`>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Privilege` {
    var childPrivileges: Array<`Schema$Privilege`>?
        get() = definedExternally
        set(value) = definedExternally
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var isOuScopable: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var privilegeName: String?
        get() = definedExternally
        set(value) = definedExternally
    var serviceId: String?
        get() = definedExternally
        set(value) = definedExternally
    var serviceName: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Privileges` {
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var items: Array<`Schema$Privilege`>?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$11` {
    var privilegeName: String?
        get() = definedExternally
        set(value) = definedExternally
    var serviceId: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Role` {
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var isSuperAdminRole: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var isSystemRole: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var roleDescription: String?
        get() = definedExternally
        set(value) = definedExternally
    var roleId: String?
        get() = definedExternally
        set(value) = definedExternally
    var roleName: String?
        get() = definedExternally
        set(value) = definedExternally
    var rolePrivileges: Array<`T$11`>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$RoleAssignment` {
    var assignedTo: String?
        get() = definedExternally
        set(value) = definedExternally
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var orgUnitId: String?
        get() = definedExternally
        set(value) = definedExternally
    var roleAssignmentId: String?
        get() = definedExternally
        set(value) = definedExternally
    var roleId: String?
        get() = definedExternally
        set(value) = definedExternally
    var scopeType: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$RoleAssignments` {
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var items: Array<`Schema$RoleAssignment`>?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var nextPageToken: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Roles` {
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var items: Array<`Schema$Role`>?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var nextPageToken: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Schema` {
    var displayName: String?
        get() = definedExternally
        set(value) = definedExternally
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var fields: Array<`Schema$SchemaFieldSpec`>?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var schemaId: String?
        get() = definedExternally
        set(value) = definedExternally
    var schemaName: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$12` {
    var maxValue: Number?
        get() = definedExternally
        set(value) = definedExternally
    var minValue: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$SchemaFieldSpec` {
    var displayName: String?
        get() = definedExternally
        set(value) = definedExternally
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var fieldId: String?
        get() = definedExternally
        set(value) = definedExternally
    var fieldName: String?
        get() = definedExternally
        set(value) = definedExternally
    var fieldType: String?
        get() = definedExternally
        set(value) = definedExternally
    var indexed: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var multiValued: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var numericIndexingSpec: dynamic /* `T$12` | Nothing? */
        get() = definedExternally
        set(value) = definedExternally
    var readAccessType: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Schemas` {
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var schemas: Array<`Schema$Schema`>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Token` {
    var anonymous: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var clientId: String?
        get() = definedExternally
        set(value) = definedExternally
    var displayText: String?
        get() = definedExternally
        set(value) = definedExternally
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var nativeApp: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var scopes: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Tokens` {
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var items: Array<`Schema$Token`>?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$TrustedAppId` {
    var androidPackageName: String?
        get() = definedExternally
        set(value) = definedExternally
    var certificateHashSHA1: String?
        get() = definedExternally
        set(value) = definedExternally
    var certificateHashSHA256: String?
        get() = definedExternally
        set(value) = definedExternally
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$TrustedApps` {
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var nextPageToken: String?
        get() = definedExternally
        set(value) = definedExternally
    var trustedApps: Array<`Schema$TrustedAppId`>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$User` {
    var addresses: Any?
        get() = definedExternally
        set(value) = definedExternally
    var agreedToTerms: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var aliases: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var archived: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var changePasswordAtNextLogin: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var creationTime: String?
        get() = definedExternally
        set(value) = definedExternally
    var customerId: String?
        get() = definedExternally
        set(value) = definedExternally
    var customSchemas: dynamic /* `T$13` | Nothing? */
        get() = definedExternally
        set(value) = definedExternally
    var deletionTime: String?
        get() = definedExternally
        set(value) = definedExternally
    var emails: Any?
        get() = definedExternally
        set(value) = definedExternally
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var externalIds: Any?
        get() = definedExternally
        set(value) = definedExternally
    var gender: Any?
        get() = definedExternally
        set(value) = definedExternally
    var hashFunction: String?
        get() = definedExternally
        set(value) = definedExternally
    var id: String?
        get() = definedExternally
        set(value) = definedExternally
    var ims: Any?
        get() = definedExternally
        set(value) = definedExternally
    var includeInGlobalAddressList: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var ipWhitelisted: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var isAdmin: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var isDelegatedAdmin: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var isEnforcedIn2Sv: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var isEnrolledIn2Sv: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var isMailboxSetup: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var keywords: Any?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var languages: Any?
        get() = definedExternally
        set(value) = definedExternally
    var lastLoginTime: String?
        get() = definedExternally
        set(value) = definedExternally
    var locations: Any?
        get() = definedExternally
        set(value) = definedExternally
    var name: `Schema$UserName`?
        get() = definedExternally
        set(value) = definedExternally
    var nonEditableAliases: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var notes: Any?
        get() = definedExternally
        set(value) = definedExternally
    var organizations: Any?
        get() = definedExternally
        set(value) = definedExternally
    var orgUnitPath: String?
        get() = definedExternally
        set(value) = definedExternally
    var password: String?
        get() = definedExternally
        set(value) = definedExternally
    var phones: Any?
        get() = definedExternally
        set(value) = definedExternally
    var posixAccounts: Any?
        get() = definedExternally
        set(value) = definedExternally
    var primaryEmail: String?
        get() = definedExternally
        set(value) = definedExternally
    var recoveryEmail: String?
        get() = definedExternally
        set(value) = definedExternally
    var recoveryPhone: String?
        get() = definedExternally
        set(value) = definedExternally
    var relations: Any?
        get() = definedExternally
        set(value) = definedExternally
    var sshPublicKeys: Any?
        get() = definedExternally
        set(value) = definedExternally
    var suspended: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var suspensionReason: String?
        get() = definedExternally
        set(value) = definedExternally
    var thumbnailPhotoEtag: String?
        get() = definedExternally
        set(value) = definedExternally
    var thumbnailPhotoUrl: String?
        get() = definedExternally
        set(value) = definedExternally
    var websites: Any?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$UserAbout` {
    var contentType: String?
        get() = definedExternally
        set(value) = definedExternally
    var value: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$UserAddress` {
    var country: String?
        get() = definedExternally
        set(value) = definedExternally
    var countryCode: String?
        get() = definedExternally
        set(value) = definedExternally
    var customType: String?
        get() = definedExternally
        set(value) = definedExternally
    var extendedAddress: String?
        get() = definedExternally
        set(value) = definedExternally
    var formatted: String?
        get() = definedExternally
        set(value) = definedExternally
    var locality: String?
        get() = definedExternally
        set(value) = definedExternally
    var poBox: String?
        get() = definedExternally
        set(value) = definedExternally
    var postalCode: String?
        get() = definedExternally
        set(value) = definedExternally
    var primary: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var region: String?
        get() = definedExternally
        set(value) = definedExternally
    var sourceIsStructured: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var streetAddress: String?
        get() = definedExternally
        set(value) = definedExternally
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$UserCustomProperties`

external interface `Schema$UserEmail` {
    var address: String?
        get() = definedExternally
        set(value) = definedExternally
    var customType: String?
        get() = definedExternally
        set(value) = definedExternally
    var primary: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$UserExternalId` {
    var customType: String?
        get() = definedExternally
        set(value) = definedExternally
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
    var value: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$UserGender` {
    var addressMeAs: String?
        get() = definedExternally
        set(value) = definedExternally
    var customGender: String?
        get() = definedExternally
        set(value) = definedExternally
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$UserIm` {
    var customProtocol: String?
        get() = definedExternally
        set(value) = definedExternally
    var customType: String?
        get() = definedExternally
        set(value) = definedExternally
    var im: String?
        get() = definedExternally
        set(value) = definedExternally
    var primary: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var protocol: String?
        get() = definedExternally
        set(value) = definedExternally
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$UserKeyword` {
    var customType: String?
        get() = definedExternally
        set(value) = definedExternally
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
    var value: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$UserLanguage` {
    var customLanguage: String?
        get() = definedExternally
        set(value) = definedExternally
    var languageCode: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$UserLocation` {
    var area: String?
        get() = definedExternally
        set(value) = definedExternally
    var buildingId: String?
        get() = definedExternally
        set(value) = definedExternally
    var customType: String?
        get() = definedExternally
        set(value) = definedExternally
    var deskCode: String?
        get() = definedExternally
        set(value) = definedExternally
    var floorName: String?
        get() = definedExternally
        set(value) = definedExternally
    var floorSection: String?
        get() = definedExternally
        set(value) = definedExternally
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$UserMakeAdmin` {
    var status: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$UserName` {
    var familyName: String?
        get() = definedExternally
        set(value) = definedExternally
    var fullName: String?
        get() = definedExternally
        set(value) = definedExternally
    var givenName: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$UserOrganization` {
    var costCenter: String?
        get() = definedExternally
        set(value) = definedExternally
    var customType: String?
        get() = definedExternally
        set(value) = definedExternally
    var department: String?
        get() = definedExternally
        set(value) = definedExternally
    var description: String?
        get() = definedExternally
        set(value) = definedExternally
    var domain: String?
        get() = definedExternally
        set(value) = definedExternally
    var fullTimeEquivalent: Number?
        get() = definedExternally
        set(value) = definedExternally
    var location: String?
        get() = definedExternally
        set(value) = definedExternally
    var name: String?
        get() = definedExternally
        set(value) = definedExternally
    var primary: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var symbol: String?
        get() = definedExternally
        set(value) = definedExternally
    var title: String?
        get() = definedExternally
        set(value) = definedExternally
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$UserPhone` {
    var customType: String?
        get() = definedExternally
        set(value) = definedExternally
    var primary: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
    var value: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$UserPhoto` {
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var height: Number?
        get() = definedExternally
        set(value) = definedExternally
    var id: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var mimeType: String?
        get() = definedExternally
        set(value) = definedExternally
    var photoData: String?
        get() = definedExternally
        set(value) = definedExternally
    var primaryEmail: String?
        get() = definedExternally
        set(value) = definedExternally
    var width: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$UserPosixAccount` {
    var accountId: String?
        get() = definedExternally
        set(value) = definedExternally
    var gecos: String?
        get() = definedExternally
        set(value) = definedExternally
    var gid: String?
        get() = definedExternally
        set(value) = definedExternally
    var homeDirectory: String?
        get() = definedExternally
        set(value) = definedExternally
    var operatingSystemType: String?
        get() = definedExternally
        set(value) = definedExternally
    var primary: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var shell: String?
        get() = definedExternally
        set(value) = definedExternally
    var systemId: String?
        get() = definedExternally
        set(value) = definedExternally
    var uid: String?
        get() = definedExternally
        set(value) = definedExternally
    var username: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$UserRelation` {
    var customType: String?
        get() = definedExternally
        set(value) = definedExternally
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
    var value: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$Users` : `Schema$Base`<`Schema$Users`> {
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var nextPageToken: String?
        get() = definedExternally
        set(value) = definedExternally
    var trigger_event: String?
        get() = definedExternally
        set(value) = definedExternally
    var users: Array<`Schema$User`>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$UserSshPublicKey` {
    var expirationTimeUsec: String?
        get() = definedExternally
        set(value) = definedExternally
    var fingerprint: String?
        get() = definedExternally
        set(value) = definedExternally
    var key: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$UserUndelete` {
    var orgUnitPath: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$UserWebsite` {
    var customType: String?
        get() = definedExternally
        set(value) = definedExternally
    var primary: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
    var value: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$VerificationCode` {
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
    var userId: String?
        get() = definedExternally
        set(value) = definedExternally
    var verificationCode: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Schema$VerificationCodes` {
    var etag: String?
        get() = definedExternally
        set(value) = definedExternally
    var items: Array<`Schema$VerificationCode`>?
        get() = definedExternally
        set(value) = definedExternally
    var kind: String?
        get() = definedExternally
        set(value) = definedExternally
}

open external class `Resource$Asps`(context: APIRequestContext) {
    open var context: APIRequestContext
    open fun delete(params: `Params$Resource$Asps$Delete`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun delete(params: `Params$Resource$Asps$Delete`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Asps$Delete`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Asps$Delete`, callback: BodyResponseCallback<Unit>)
    open fun delete(callback: BodyResponseCallback<Unit>)
    open fun get(params: `Params$Resource$Asps$Get`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Asp`>
    open fun get(params: `Params$Resource$Asps$Get`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Asp`>)
    open fun get(params: `Params$Resource$Asps$Get`, options: BodyResponseCallback<`Schema$Asp`>, callback: BodyResponseCallback<`Schema$Asp`>)
    open fun get(params: `Params$Resource$Asps$Get`, callback: BodyResponseCallback<`Schema$Asp`>)
    open fun get(callback: BodyResponseCallback<`Schema$Asp`>)
    open fun list(params: `Params$Resource$Asps$List`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Asps`>
    open fun list(params: `Params$Resource$Asps$List`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Asps`>)
    open fun list(params: `Params$Resource$Asps$List`, options: BodyResponseCallback<`Schema$Asps`>, callback: BodyResponseCallback<`Schema$Asps`>)
    open fun list(params: `Params$Resource$Asps$List`, callback: BodyResponseCallback<`Schema$Asps`>)
    open fun list(callback: BodyResponseCallback<`Schema$Asps`>)
}

external interface `Params$Resource$Asps$Delete` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var codeId: Number?
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Asps$Get` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var codeId: Number?
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Asps$List` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

open external class `Resource$Channels`(context: APIRequestContext) {
    open var context: APIRequestContext
    open fun stop(params: `Params$Resource$Channels$Stop`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun stop(params: `Params$Resource$Channels$Stop`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun stop(params: `Params$Resource$Channels$Stop`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun stop(params: `Params$Resource$Channels$Stop`, callback: BodyResponseCallback<Unit>)
    open fun stop(callback: BodyResponseCallback<Unit>)
}

external interface `Params$Resource$Channels$Stop` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Channel`?
        get() = definedExternally
        set(value) = definedExternally
}

open external class `Resource$Chromeosdevices`(context: APIRequestContext) {
    open var context: APIRequestContext
    open fun action(params: `Params$Resource$Chromeosdevices$Action`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun action(params: `Params$Resource$Chromeosdevices$Action`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun action(params: `Params$Resource$Chromeosdevices$Action`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun action(params: `Params$Resource$Chromeosdevices$Action`, callback: BodyResponseCallback<Unit>)
    open fun action(callback: BodyResponseCallback<Unit>)
    open fun get(params: `Params$Resource$Chromeosdevices$Get`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$ChromeOsDevice`>
    open fun get(params: `Params$Resource$Chromeosdevices$Get`, options: MethodOptions, callback: BodyResponseCallback<`Schema$ChromeOsDevice`>)
    open fun get(params: `Params$Resource$Chromeosdevices$Get`, options: BodyResponseCallback<`Schema$ChromeOsDevice`>, callback: BodyResponseCallback<`Schema$ChromeOsDevice`>)
    open fun get(params: `Params$Resource$Chromeosdevices$Get`, callback: BodyResponseCallback<`Schema$ChromeOsDevice`>)
    open fun get(callback: BodyResponseCallback<`Schema$ChromeOsDevice`>)
    open fun list(params: `Params$Resource$Chromeosdevices$List`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$ChromeOsDevices`>
    open fun list(params: `Params$Resource$Chromeosdevices$List`, options: MethodOptions, callback: BodyResponseCallback<`Schema$ChromeOsDevices`>)
    open fun list(params: `Params$Resource$Chromeosdevices$List`, options: BodyResponseCallback<`Schema$ChromeOsDevices`>, callback: BodyResponseCallback<`Schema$ChromeOsDevices`>)
    open fun list(params: `Params$Resource$Chromeosdevices$List`, callback: BodyResponseCallback<`Schema$ChromeOsDevices`>)
    open fun list(callback: BodyResponseCallback<`Schema$ChromeOsDevices`>)
    open fun moveDevicesToOu(params: `Params$Resource$Chromeosdevices$Movedevicestoou`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun moveDevicesToOu(params: `Params$Resource$Chromeosdevices$Movedevicestoou`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun moveDevicesToOu(params: `Params$Resource$Chromeosdevices$Movedevicestoou`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun moveDevicesToOu(params: `Params$Resource$Chromeosdevices$Movedevicestoou`, callback: BodyResponseCallback<Unit>)
    open fun moveDevicesToOu(callback: BodyResponseCallback<Unit>)
    open fun patch(params: `Params$Resource$Chromeosdevices$Patch`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$ChromeOsDevice`>
    open fun patch(params: `Params$Resource$Chromeosdevices$Patch`, options: MethodOptions, callback: BodyResponseCallback<`Schema$ChromeOsDevice`>)
    open fun patch(params: `Params$Resource$Chromeosdevices$Patch`, options: BodyResponseCallback<`Schema$ChromeOsDevice`>, callback: BodyResponseCallback<`Schema$ChromeOsDevice`>)
    open fun patch(params: `Params$Resource$Chromeosdevices$Patch`, callback: BodyResponseCallback<`Schema$ChromeOsDevice`>)
    open fun patch(callback: BodyResponseCallback<`Schema$ChromeOsDevice`>)
    open fun update(params: `Params$Resource$Chromeosdevices$Update`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$ChromeOsDevice`>
    open fun update(params: `Params$Resource$Chromeosdevices$Update`, options: MethodOptions, callback: BodyResponseCallback<`Schema$ChromeOsDevice`>)
    open fun update(params: `Params$Resource$Chromeosdevices$Update`, options: BodyResponseCallback<`Schema$ChromeOsDevice`>, callback: BodyResponseCallback<`Schema$ChromeOsDevice`>)
    open fun update(params: `Params$Resource$Chromeosdevices$Update`, callback: BodyResponseCallback<`Schema$ChromeOsDevice`>)
    open fun update(callback: BodyResponseCallback<`Schema$ChromeOsDevice`>)
}

external interface `Params$Resource$Chromeosdevices$Action` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customerId: String?
        get() = definedExternally
        set(value) = definedExternally
    var resourceId: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$ChromeOsDeviceAction`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Chromeosdevices$Get` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customerId: String?
        get() = definedExternally
        set(value) = definedExternally
    var deviceId: String?
        get() = definedExternally
        set(value) = definedExternally
    var projection: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Chromeosdevices$List` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customerId: String?
        get() = definedExternally
        set(value) = definedExternally
    var maxResults: Number?
        get() = definedExternally
        set(value) = definedExternally
    var orderBy: String?
        get() = definedExternally
        set(value) = definedExternally
    var orgUnitPath: String?
        get() = definedExternally
        set(value) = definedExternally
    var pageToken: String?
        get() = definedExternally
        set(value) = definedExternally
    var projection: String?
        get() = definedExternally
        set(value) = definedExternally
    var query: String?
        get() = definedExternally
        set(value) = definedExternally
    var sortOrder: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Chromeosdevices$Movedevicestoou` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customerId: String?
        get() = definedExternally
        set(value) = definedExternally
    var orgUnitPath: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$ChromeOsMoveDevicesToOu`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Chromeosdevices$Patch` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customerId: String?
        get() = definedExternally
        set(value) = definedExternally
    var deviceId: String?
        get() = definedExternally
        set(value) = definedExternally
    var projection: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$ChromeOsDevice`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Chromeosdevices$Update` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customerId: String?
        get() = definedExternally
        set(value) = definedExternally
    var deviceId: String?
        get() = definedExternally
        set(value) = definedExternally
    var projection: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$ChromeOsDevice`?
        get() = definedExternally
        set(value) = definedExternally
}

open external class `Resource$Customers`(context: APIRequestContext) {
    open var context: APIRequestContext
    open fun get(params: `Params$Resource$Customers$Get`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Customer`>
    open fun get(params: `Params$Resource$Customers$Get`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Customer`>)
    open fun get(params: `Params$Resource$Customers$Get`, options: BodyResponseCallback<`Schema$Customer`>, callback: BodyResponseCallback<`Schema$Customer`>)
    open fun get(params: `Params$Resource$Customers$Get`, callback: BodyResponseCallback<`Schema$Customer`>)
    open fun get(callback: BodyResponseCallback<`Schema$Customer`>)
    open fun patch(params: `Params$Resource$Customers$Patch`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Customer`>
    open fun patch(params: `Params$Resource$Customers$Patch`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Customer`>)
    open fun patch(params: `Params$Resource$Customers$Patch`, options: BodyResponseCallback<`Schema$Customer`>, callback: BodyResponseCallback<`Schema$Customer`>)
    open fun patch(params: `Params$Resource$Customers$Patch`, callback: BodyResponseCallback<`Schema$Customer`>)
    open fun patch(callback: BodyResponseCallback<`Schema$Customer`>)
    open fun update(params: `Params$Resource$Customers$Update`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Customer`>
    open fun update(params: `Params$Resource$Customers$Update`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Customer`>)
    open fun update(params: `Params$Resource$Customers$Update`, options: BodyResponseCallback<`Schema$Customer`>, callback: BodyResponseCallback<`Schema$Customer`>)
    open fun update(params: `Params$Resource$Customers$Update`, callback: BodyResponseCallback<`Schema$Customer`>)
    open fun update(callback: BodyResponseCallback<`Schema$Customer`>)
}

external interface `Params$Resource$Customers$Get` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customerKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Customers$Patch` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customerKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Customer`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Customers$Update` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customerKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Customer`?
        get() = definedExternally
        set(value) = definedExternally
}

open external class `Resource$Domainaliases`(context: APIRequestContext) {
    open var context: APIRequestContext
    open fun delete(params: `Params$Resource$Domainaliases$Delete`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun delete(params: `Params$Resource$Domainaliases$Delete`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Domainaliases$Delete`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Domainaliases$Delete`, callback: BodyResponseCallback<Unit>)
    open fun delete(callback: BodyResponseCallback<Unit>)
    open fun get(params: `Params$Resource$Domainaliases$Get`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$DomainAlias`>
    open fun get(params: `Params$Resource$Domainaliases$Get`, options: MethodOptions, callback: BodyResponseCallback<`Schema$DomainAlias`>)
    open fun get(params: `Params$Resource$Domainaliases$Get`, options: BodyResponseCallback<`Schema$DomainAlias`>, callback: BodyResponseCallback<`Schema$DomainAlias`>)
    open fun get(params: `Params$Resource$Domainaliases$Get`, callback: BodyResponseCallback<`Schema$DomainAlias`>)
    open fun get(callback: BodyResponseCallback<`Schema$DomainAlias`>)
    open fun insert(params: `Params$Resource$Domainaliases$Insert`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$DomainAlias`>
    open fun insert(params: `Params$Resource$Domainaliases$Insert`, options: MethodOptions, callback: BodyResponseCallback<`Schema$DomainAlias`>)
    open fun insert(params: `Params$Resource$Domainaliases$Insert`, options: BodyResponseCallback<`Schema$DomainAlias`>, callback: BodyResponseCallback<`Schema$DomainAlias`>)
    open fun insert(params: `Params$Resource$Domainaliases$Insert`, callback: BodyResponseCallback<`Schema$DomainAlias`>)
    open fun insert(callback: BodyResponseCallback<`Schema$DomainAlias`>)
    open fun list(params: `Params$Resource$Domainaliases$List`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$DomainAliases`>
    open fun list(params: `Params$Resource$Domainaliases$List`, options: MethodOptions, callback: BodyResponseCallback<`Schema$DomainAliases`>)
    open fun list(params: `Params$Resource$Domainaliases$List`, options: BodyResponseCallback<`Schema$DomainAliases`>, callback: BodyResponseCallback<`Schema$DomainAliases`>)
    open fun list(params: `Params$Resource$Domainaliases$List`, callback: BodyResponseCallback<`Schema$DomainAliases`>)
    open fun list(callback: BodyResponseCallback<`Schema$DomainAliases`>)
}

external interface `Params$Resource$Domainaliases$Delete` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var domainAliasName: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Domainaliases$Get` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var domainAliasName: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Domainaliases$Insert` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$DomainAlias`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Domainaliases$List` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var parentDomainName: String?
        get() = definedExternally
        set(value) = definedExternally
}

open external class `Resource$Domains`(context: APIRequestContext) {
    open var context: APIRequestContext
    open fun delete(params: `Params$Resource$Domains$Delete`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun delete(params: `Params$Resource$Domains$Delete`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Domains$Delete`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Domains$Delete`, callback: BodyResponseCallback<Unit>)
    open fun delete(callback: BodyResponseCallback<Unit>)
    open fun get(params: `Params$Resource$Domains$Get`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Domains`>
    open fun get(params: `Params$Resource$Domains$Get`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Domains`>)
    open fun get(params: `Params$Resource$Domains$Get`, options: BodyResponseCallback<`Schema$Domains`>, callback: BodyResponseCallback<`Schema$Domains`>)
    open fun get(params: `Params$Resource$Domains$Get`, callback: BodyResponseCallback<`Schema$Domains`>)
    open fun get(callback: BodyResponseCallback<`Schema$Domains`>)
    open fun insert(params: `Params$Resource$Domains$Insert`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Domains`>
    open fun insert(params: `Params$Resource$Domains$Insert`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Domains`>)
    open fun insert(params: `Params$Resource$Domains$Insert`, options: BodyResponseCallback<`Schema$Domains`>, callback: BodyResponseCallback<`Schema$Domains`>)
    open fun insert(params: `Params$Resource$Domains$Insert`, callback: BodyResponseCallback<`Schema$Domains`>)
    open fun insert(callback: BodyResponseCallback<`Schema$Domains`>)
    open fun list(params: `Params$Resource$Domains$List`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Domains2`>
    open fun list(params: `Params$Resource$Domains$List`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Domains2`>)
    open fun list(params: `Params$Resource$Domains$List`, options: BodyResponseCallback<`Schema$Domains2`>, callback: BodyResponseCallback<`Schema$Domains2`>)
    open fun list(params: `Params$Resource$Domains$List`, callback: BodyResponseCallback<`Schema$Domains2`>)
    open fun list(callback: BodyResponseCallback<`Schema$Domains2`>)
}

external interface `Params$Resource$Domains$Delete` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var domainName: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Domains$Get` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var domainName: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Domains$Insert` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Domains`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Domains$List` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
}

open external class `Resource$Groups`(context: APIRequestContext) {
    open var context: APIRequestContext
    open var aliases: `Resource$Groups$Aliases`
    open fun delete(params: `Params$Resource$Groups$Delete`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun delete(params: `Params$Resource$Groups$Delete`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Groups$Delete`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Groups$Delete`, callback: BodyResponseCallback<Unit>)
    open fun delete(callback: BodyResponseCallback<Unit>)
    open fun get(params: `Params$Resource$Groups$Get`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Group`>
    open fun get(params: `Params$Resource$Groups$Get`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Group`>)
    open fun get(params: `Params$Resource$Groups$Get`, options: BodyResponseCallback<`Schema$Group`>, callback: BodyResponseCallback<`Schema$Group`>)
    open fun get(params: `Params$Resource$Groups$Get`, callback: BodyResponseCallback<`Schema$Group`>)
    open fun get(callback: BodyResponseCallback<`Schema$Group`>)
    open fun insert(params: `Params$Resource$Groups$Insert`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Group`>
    open fun insert(params: `Params$Resource$Groups$Insert`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Group`>)
    open fun insert(params: `Params$Resource$Groups$Insert`, options: BodyResponseCallback<`Schema$Group`>, callback: BodyResponseCallback<`Schema$Group`>)
    open fun insert(params: `Params$Resource$Groups$Insert`, callback: BodyResponseCallback<`Schema$Group`>)
    open fun insert(callback: BodyResponseCallback<`Schema$Group`>)
    open fun list(params: `Params$Resource$Groups$List`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Groups`>
    open fun list(params: `Params$Resource$Groups$List`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Groups`>)
    open fun list(params: `Params$Resource$Groups$List`, options: BodyResponseCallback<`Schema$Groups`>, callback: BodyResponseCallback<`Schema$Groups`>)
    open fun list(params: `Params$Resource$Groups$List`, callback: BodyResponseCallback<`Schema$Groups`>)
    open fun list(callback: BodyResponseCallback<`Schema$Groups`>)
    open fun patch(params: `Params$Resource$Groups$Patch`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Group`>
    open fun patch(params: `Params$Resource$Groups$Patch`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Group`>)
    open fun patch(params: `Params$Resource$Groups$Patch`, options: BodyResponseCallback<`Schema$Group`>, callback: BodyResponseCallback<`Schema$Group`>)
    open fun patch(params: `Params$Resource$Groups$Patch`, callback: BodyResponseCallback<`Schema$Group`>)
    open fun patch(callback: BodyResponseCallback<`Schema$Group`>)
    open fun update(params: `Params$Resource$Groups$Update`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Group`>
    open fun update(params: `Params$Resource$Groups$Update`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Group`>)
    open fun update(params: `Params$Resource$Groups$Update`, options: BodyResponseCallback<`Schema$Group`>, callback: BodyResponseCallback<`Schema$Group`>)
    open fun update(params: `Params$Resource$Groups$Update`, callback: BodyResponseCallback<`Schema$Group`>)
    open fun update(callback: BodyResponseCallback<`Schema$Group`>)
}

external interface `Params$Resource$Groups$Delete` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var groupKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Groups$Get` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var groupKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Groups$Insert` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Group`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Groups$List` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var domain: String?
        get() = definedExternally
        set(value) = definedExternally
    var maxResults: Number?
        get() = definedExternally
        set(value) = definedExternally
    var orderBy: String?
        get() = definedExternally
        set(value) = definedExternally
    var pageToken: String?
        get() = definedExternally
        set(value) = definedExternally
    var query: String?
        get() = definedExternally
        set(value) = definedExternally
    var sortOrder: String?
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Groups$Patch` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var groupKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Group`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Groups$Update` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var groupKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Group`?
        get() = definedExternally
        set(value) = definedExternally
}

open external class `Resource$Groups$Aliases`(context: APIRequestContext) {
    open var context: APIRequestContext
    open fun delete(params: `Params$Resource$Groups$Aliases$Delete`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun delete(params: `Params$Resource$Groups$Aliases$Delete`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Groups$Aliases$Delete`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Groups$Aliases$Delete`, callback: BodyResponseCallback<Unit>)
    open fun delete(callback: BodyResponseCallback<Unit>)
    open fun insert(params: `Params$Resource$Groups$Aliases$Insert`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Alias`>
    open fun insert(params: `Params$Resource$Groups$Aliases$Insert`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Alias`>)
    open fun insert(params: `Params$Resource$Groups$Aliases$Insert`, options: BodyResponseCallback<`Schema$Alias`>, callback: BodyResponseCallback<`Schema$Alias`>)
    open fun insert(params: `Params$Resource$Groups$Aliases$Insert`, callback: BodyResponseCallback<`Schema$Alias`>)
    open fun insert(callback: BodyResponseCallback<`Schema$Alias`>)
    open fun list(params: `Params$Resource$Groups$Aliases$List`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Aliases`>
    open fun list(params: `Params$Resource$Groups$Aliases$List`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Aliases`>)
    open fun list(params: `Params$Resource$Groups$Aliases$List`, options: BodyResponseCallback<`Schema$Aliases`>, callback: BodyResponseCallback<`Schema$Aliases`>)
    open fun list(params: `Params$Resource$Groups$Aliases$List`, callback: BodyResponseCallback<`Schema$Aliases`>)
    open fun list(callback: BodyResponseCallback<`Schema$Aliases`>)
}

external interface `Params$Resource$Groups$Aliases$Delete` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var alias: String?
        get() = definedExternally
        set(value) = definedExternally
    var groupKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Groups$Aliases$Insert` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var groupKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Alias`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Groups$Aliases$List` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var groupKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

open external class `Resource$Members`(context: APIRequestContext) {
    open var context: APIRequestContext
    open fun delete(params: `Params$Resource$Members$Delete`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun delete(params: `Params$Resource$Members$Delete`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Members$Delete`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Members$Delete`, callback: BodyResponseCallback<Unit>)
    open fun delete(callback: BodyResponseCallback<Unit>)
    open fun get(params: `Params$Resource$Members$Get`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Member`>
    open fun get(params: `Params$Resource$Members$Get`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Member`>)
    open fun get(params: `Params$Resource$Members$Get`, options: BodyResponseCallback<`Schema$Member`>, callback: BodyResponseCallback<`Schema$Member`>)
    open fun get(params: `Params$Resource$Members$Get`, callback: BodyResponseCallback<`Schema$Member`>)
    open fun get(callback: BodyResponseCallback<`Schema$Member`>)
    open fun hasMember(params: `Params$Resource$Members$Hasmember`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$MembersHasMember`>
    open fun hasMember(params: `Params$Resource$Members$Hasmember`, options: MethodOptions, callback: BodyResponseCallback<`Schema$MembersHasMember`>)
    open fun hasMember(params: `Params$Resource$Members$Hasmember`, options: BodyResponseCallback<`Schema$MembersHasMember`>, callback: BodyResponseCallback<`Schema$MembersHasMember`>)
    open fun hasMember(params: `Params$Resource$Members$Hasmember`, callback: BodyResponseCallback<`Schema$MembersHasMember`>)
    open fun hasMember(callback: BodyResponseCallback<`Schema$MembersHasMember`>)
    open fun insert(params: `Params$Resource$Members$Insert`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Member`>
    open fun insert(params: `Params$Resource$Members$Insert`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Member`>)
    open fun insert(params: `Params$Resource$Members$Insert`, options: BodyResponseCallback<`Schema$Member`>, callback: BodyResponseCallback<`Schema$Member`>)
    open fun insert(params: `Params$Resource$Members$Insert`, callback: BodyResponseCallback<`Schema$Member`>)
    open fun insert(callback: BodyResponseCallback<`Schema$Member`>)
    open fun list(params: `Params$Resource$Members$List`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Members`>
    open fun list(params: `Params$Resource$Members$List`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Members`>)
    open fun list(params: `Params$Resource$Members$List`, options: BodyResponseCallback<`Schema$Members`>, callback: BodyResponseCallback<`Schema$Members`>)
    open fun list(params: `Params$Resource$Members$List`, callback: BodyResponseCallback<`Schema$Members`>)
    open fun list(callback: BodyResponseCallback<`Schema$Members`>)
    open fun patch(params: `Params$Resource$Members$Patch`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Member`>
    open fun patch(params: `Params$Resource$Members$Patch`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Member`>)
    open fun patch(params: `Params$Resource$Members$Patch`, options: BodyResponseCallback<`Schema$Member`>, callback: BodyResponseCallback<`Schema$Member`>)
    open fun patch(params: `Params$Resource$Members$Patch`, callback: BodyResponseCallback<`Schema$Member`>)
    open fun patch(callback: BodyResponseCallback<`Schema$Member`>)
    open fun update(params: `Params$Resource$Members$Update`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Member`>
    open fun update(params: `Params$Resource$Members$Update`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Member`>)
    open fun update(params: `Params$Resource$Members$Update`, options: BodyResponseCallback<`Schema$Member`>, callback: BodyResponseCallback<`Schema$Member`>)
    open fun update(params: `Params$Resource$Members$Update`, callback: BodyResponseCallback<`Schema$Member`>)
    open fun update(callback: BodyResponseCallback<`Schema$Member`>)
}

external interface `Params$Resource$Members$Delete` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var groupKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var memberKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Members$Get` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var groupKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var memberKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Members$Hasmember` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var groupKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var memberKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Members$Insert` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var groupKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Member`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Members$List` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var groupKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var includeDerivedMembership: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var maxResults: Number?
        get() = definedExternally
        set(value) = definedExternally
    var pageToken: String?
        get() = definedExternally
        set(value) = definedExternally
    var roles: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Members$Patch` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var groupKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var memberKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Member`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Members$Update` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var groupKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var memberKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Member`?
        get() = definedExternally
        set(value) = definedExternally
}

open external class `Resource$Mobiledevices`(context: APIRequestContext) {
    open var context: APIRequestContext
    open fun action(params: `Params$Resource$Mobiledevices$Action`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun action(params: `Params$Resource$Mobiledevices$Action`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun action(params: `Params$Resource$Mobiledevices$Action`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun action(params: `Params$Resource$Mobiledevices$Action`, callback: BodyResponseCallback<Unit>)
    open fun action(callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Mobiledevices$Delete`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun delete(params: `Params$Resource$Mobiledevices$Delete`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Mobiledevices$Delete`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Mobiledevices$Delete`, callback: BodyResponseCallback<Unit>)
    open fun delete(callback: BodyResponseCallback<Unit>)
    open fun get(params: `Params$Resource$Mobiledevices$Get`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$MobileDevice`>
    open fun get(params: `Params$Resource$Mobiledevices$Get`, options: MethodOptions, callback: BodyResponseCallback<`Schema$MobileDevice`>)
    open fun get(params: `Params$Resource$Mobiledevices$Get`, options: BodyResponseCallback<`Schema$MobileDevice`>, callback: BodyResponseCallback<`Schema$MobileDevice`>)
    open fun get(params: `Params$Resource$Mobiledevices$Get`, callback: BodyResponseCallback<`Schema$MobileDevice`>)
    open fun get(callback: BodyResponseCallback<`Schema$MobileDevice`>)
    open fun list(params: `Params$Resource$Mobiledevices$List`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$MobileDevices`>
    open fun list(params: `Params$Resource$Mobiledevices$List`, options: MethodOptions, callback: BodyResponseCallback<`Schema$MobileDevices`>)
    open fun list(params: `Params$Resource$Mobiledevices$List`, options: BodyResponseCallback<`Schema$MobileDevices`>, callback: BodyResponseCallback<`Schema$MobileDevices`>)
    open fun list(params: `Params$Resource$Mobiledevices$List`, callback: BodyResponseCallback<`Schema$MobileDevices`>)
    open fun list(callback: BodyResponseCallback<`Schema$MobileDevices`>)
}

external interface `Params$Resource$Mobiledevices$Action` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customerId: String?
        get() = definedExternally
        set(value) = definedExternally
    var resourceId: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$MobileDeviceAction`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Mobiledevices$Delete` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customerId: String?
        get() = definedExternally
        set(value) = definedExternally
    var resourceId: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Mobiledevices$Get` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customerId: String?
        get() = definedExternally
        set(value) = definedExternally
    var projection: String?
        get() = definedExternally
        set(value) = definedExternally
    var resourceId: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Mobiledevices$List` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customerId: String?
        get() = definedExternally
        set(value) = definedExternally
    var maxResults: Number?
        get() = definedExternally
        set(value) = definedExternally
    var orderBy: String?
        get() = definedExternally
        set(value) = definedExternally
    var pageToken: String?
        get() = definedExternally
        set(value) = definedExternally
    var projection: String?
        get() = definedExternally
        set(value) = definedExternally
    var query: String?
        get() = definedExternally
        set(value) = definedExternally
    var sortOrder: String?
        get() = definedExternally
        set(value) = definedExternally
}

open external class `Resource$Notifications`(context: APIRequestContext) {
    open var context: APIRequestContext
    open fun delete(params: `Params$Resource$Notifications$Delete`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun delete(params: `Params$Resource$Notifications$Delete`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Notifications$Delete`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Notifications$Delete`, callback: BodyResponseCallback<Unit>)
    open fun delete(callback: BodyResponseCallback<Unit>)
    open fun get(params: `Params$Resource$Notifications$Get`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Notification`>
    open fun get(params: `Params$Resource$Notifications$Get`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Notification`>)
    open fun get(params: `Params$Resource$Notifications$Get`, options: BodyResponseCallback<`Schema$Notification`>, callback: BodyResponseCallback<`Schema$Notification`>)
    open fun get(params: `Params$Resource$Notifications$Get`, callback: BodyResponseCallback<`Schema$Notification`>)
    open fun get(callback: BodyResponseCallback<`Schema$Notification`>)
    open fun list(params: `Params$Resource$Notifications$List`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Notifications`>
    open fun list(params: `Params$Resource$Notifications$List`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Notifications`>)
    open fun list(params: `Params$Resource$Notifications$List`, options: BodyResponseCallback<`Schema$Notifications`>, callback: BodyResponseCallback<`Schema$Notifications`>)
    open fun list(params: `Params$Resource$Notifications$List`, callback: BodyResponseCallback<`Schema$Notifications`>)
    open fun list(callback: BodyResponseCallback<`Schema$Notifications`>)
    open fun patch(params: `Params$Resource$Notifications$Patch`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Notification`>
    open fun patch(params: `Params$Resource$Notifications$Patch`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Notification`>)
    open fun patch(params: `Params$Resource$Notifications$Patch`, options: BodyResponseCallback<`Schema$Notification`>, callback: BodyResponseCallback<`Schema$Notification`>)
    open fun patch(params: `Params$Resource$Notifications$Patch`, callback: BodyResponseCallback<`Schema$Notification`>)
    open fun patch(callback: BodyResponseCallback<`Schema$Notification`>)
    open fun update(params: `Params$Resource$Notifications$Update`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Notification`>
    open fun update(params: `Params$Resource$Notifications$Update`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Notification`>)
    open fun update(params: `Params$Resource$Notifications$Update`, options: BodyResponseCallback<`Schema$Notification`>, callback: BodyResponseCallback<`Schema$Notification`>)
    open fun update(params: `Params$Resource$Notifications$Update`, callback: BodyResponseCallback<`Schema$Notification`>)
    open fun update(callback: BodyResponseCallback<`Schema$Notification`>)
}

external interface `Params$Resource$Notifications$Delete` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var notificationId: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Notifications$Get` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var notificationId: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Notifications$List` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var language: String?
        get() = definedExternally
        set(value) = definedExternally
    var maxResults: Number?
        get() = definedExternally
        set(value) = definedExternally
    var pageToken: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Notifications$Patch` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var notificationId: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Notification`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Notifications$Update` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var notificationId: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Notification`?
        get() = definedExternally
        set(value) = definedExternally
}

open external class `Resource$Orgunits`(context: APIRequestContext) {
    open var context: APIRequestContext
    open fun delete(params: `Params$Resource$Orgunits$Delete`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun delete(params: `Params$Resource$Orgunits$Delete`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Orgunits$Delete`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Orgunits$Delete`, callback: BodyResponseCallback<Unit>)
    open fun delete(callback: BodyResponseCallback<Unit>)
    open fun get(params: `Params$Resource$Orgunits$Get`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$OrgUnit`>
    open fun get(params: `Params$Resource$Orgunits$Get`, options: MethodOptions, callback: BodyResponseCallback<`Schema$OrgUnit`>)
    open fun get(params: `Params$Resource$Orgunits$Get`, options: BodyResponseCallback<`Schema$OrgUnit`>, callback: BodyResponseCallback<`Schema$OrgUnit`>)
    open fun get(params: `Params$Resource$Orgunits$Get`, callback: BodyResponseCallback<`Schema$OrgUnit`>)
    open fun get(callback: BodyResponseCallback<`Schema$OrgUnit`>)
    open fun insert(params: `Params$Resource$Orgunits$Insert`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$OrgUnit`>
    open fun insert(params: `Params$Resource$Orgunits$Insert`, options: MethodOptions, callback: BodyResponseCallback<`Schema$OrgUnit`>)
    open fun insert(params: `Params$Resource$Orgunits$Insert`, options: BodyResponseCallback<`Schema$OrgUnit`>, callback: BodyResponseCallback<`Schema$OrgUnit`>)
    open fun insert(params: `Params$Resource$Orgunits$Insert`, callback: BodyResponseCallback<`Schema$OrgUnit`>)
    open fun insert(callback: BodyResponseCallback<`Schema$OrgUnit`>)
    open fun list(params: `Params$Resource$Orgunits$List`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$OrgUnits`>
    open fun list(params: `Params$Resource$Orgunits$List`, options: MethodOptions, callback: BodyResponseCallback<`Schema$OrgUnits`>)
    open fun list(params: `Params$Resource$Orgunits$List`, options: BodyResponseCallback<`Schema$OrgUnits`>, callback: BodyResponseCallback<`Schema$OrgUnits`>)
    open fun list(params: `Params$Resource$Orgunits$List`, callback: BodyResponseCallback<`Schema$OrgUnits`>)
    open fun list(callback: BodyResponseCallback<`Schema$OrgUnits`>)
    open fun patch(params: `Params$Resource$Orgunits$Patch`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$OrgUnit`>
    open fun patch(params: `Params$Resource$Orgunits$Patch`, options: MethodOptions, callback: BodyResponseCallback<`Schema$OrgUnit`>)
    open fun patch(params: `Params$Resource$Orgunits$Patch`, options: BodyResponseCallback<`Schema$OrgUnit`>, callback: BodyResponseCallback<`Schema$OrgUnit`>)
    open fun patch(params: `Params$Resource$Orgunits$Patch`, callback: BodyResponseCallback<`Schema$OrgUnit`>)
    open fun patch(callback: BodyResponseCallback<`Schema$OrgUnit`>)
    open fun update(params: `Params$Resource$Orgunits$Update`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$OrgUnit`>
    open fun update(params: `Params$Resource$Orgunits$Update`, options: MethodOptions, callback: BodyResponseCallback<`Schema$OrgUnit`>)
    open fun update(params: `Params$Resource$Orgunits$Update`, options: BodyResponseCallback<`Schema$OrgUnit`>, callback: BodyResponseCallback<`Schema$OrgUnit`>)
    open fun update(params: `Params$Resource$Orgunits$Update`, callback: BodyResponseCallback<`Schema$OrgUnit`>)
    open fun update(callback: BodyResponseCallback<`Schema$OrgUnit`>)
}

external interface `Params$Resource$Orgunits$Delete` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customerId: String?
        get() = definedExternally
        set(value) = definedExternally
    var orgUnitPath: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Orgunits$Get` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customerId: String?
        get() = definedExternally
        set(value) = definedExternally
    var orgUnitPath: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Orgunits$Insert` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customerId: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$OrgUnit`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Orgunits$List` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customerId: String?
        get() = definedExternally
        set(value) = definedExternally
    var orgUnitPath: String?
        get() = definedExternally
        set(value) = definedExternally
    var type: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Orgunits$Patch` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customerId: String?
        get() = definedExternally
        set(value) = definedExternally
    var orgUnitPath: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$OrgUnit`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Orgunits$Update` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customerId: String?
        get() = definedExternally
        set(value) = definedExternally
    var orgUnitPath: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$OrgUnit`?
        get() = definedExternally
        set(value) = definedExternally
}

open external class `Resource$Privileges`(context: APIRequestContext) {
    open var context: APIRequestContext
    open fun list(params: `Params$Resource$Privileges$List`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Privileges`>
    open fun list(params: `Params$Resource$Privileges$List`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Privileges`>)
    open fun list(params: `Params$Resource$Privileges$List`, options: BodyResponseCallback<`Schema$Privileges`>, callback: BodyResponseCallback<`Schema$Privileges`>)
    open fun list(params: `Params$Resource$Privileges$List`, callback: BodyResponseCallback<`Schema$Privileges`>)
    open fun list(callback: BodyResponseCallback<`Schema$Privileges`>)
}

external interface `Params$Resource$Privileges$List` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
}

open external class `Resource$Resolvedappaccesssettings`(context: APIRequestContext) {
    open var context: APIRequestContext
    open fun GetSettings(params: `Params$Resource$Resolvedappaccesssettings$Getsettings`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$AppAccessCollections`>
    open fun GetSettings(params: `Params$Resource$Resolvedappaccesssettings$Getsettings`, options: MethodOptions, callback: BodyResponseCallback<`Schema$AppAccessCollections`>)
    open fun GetSettings(params: `Params$Resource$Resolvedappaccesssettings$Getsettings`, options: BodyResponseCallback<`Schema$AppAccessCollections`>, callback: BodyResponseCallback<`Schema$AppAccessCollections`>)
    open fun GetSettings(params: `Params$Resource$Resolvedappaccesssettings$Getsettings`, callback: BodyResponseCallback<`Schema$AppAccessCollections`>)
    open fun GetSettings(callback: BodyResponseCallback<`Schema$AppAccessCollections`>)
    open fun ListTrustedApps(params: `Params$Resource$Resolvedappaccesssettings$Listtrustedapps`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$TrustedApps`>
    open fun ListTrustedApps(params: `Params$Resource$Resolvedappaccesssettings$Listtrustedapps`, options: MethodOptions, callback: BodyResponseCallback<`Schema$TrustedApps`>)
    open fun ListTrustedApps(params: `Params$Resource$Resolvedappaccesssettings$Listtrustedapps`, options: BodyResponseCallback<`Schema$TrustedApps`>, callback: BodyResponseCallback<`Schema$TrustedApps`>)
    open fun ListTrustedApps(params: `Params$Resource$Resolvedappaccesssettings$Listtrustedapps`, callback: BodyResponseCallback<`Schema$TrustedApps`>)
    open fun ListTrustedApps(callback: BodyResponseCallback<`Schema$TrustedApps`>)
}

external interface `Params$Resource$Resolvedappaccesssettings$Getsettings` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Resolvedappaccesssettings$Listtrustedapps` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
}

open external class `Resource$Resources`(context: APIRequestContext) {
    open var context: APIRequestContext
    open var buildings: `Resource$Resources$Buildings`
    open var calendars: `Resource$Resources$Calendars`
    open var features: `Resource$Resources$Features`
}

open external class `Resource$Resources$Buildings`(context: APIRequestContext) {
    open var context: APIRequestContext
    open fun delete(params: `Params$Resource$Resources$Buildings$Delete`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun delete(params: `Params$Resource$Resources$Buildings$Delete`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Resources$Buildings$Delete`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Resources$Buildings$Delete`, callback: BodyResponseCallback<Unit>)
    open fun delete(callback: BodyResponseCallback<Unit>)
    open fun get(params: `Params$Resource$Resources$Buildings$Get`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Building`>
    open fun get(params: `Params$Resource$Resources$Buildings$Get`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Building`>)
    open fun get(params: `Params$Resource$Resources$Buildings$Get`, options: BodyResponseCallback<`Schema$Building`>, callback: BodyResponseCallback<`Schema$Building`>)
    open fun get(params: `Params$Resource$Resources$Buildings$Get`, callback: BodyResponseCallback<`Schema$Building`>)
    open fun get(callback: BodyResponseCallback<`Schema$Building`>)
    open fun insert(params: `Params$Resource$Resources$Buildings$Insert`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Building`>
    open fun insert(params: `Params$Resource$Resources$Buildings$Insert`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Building`>)
    open fun insert(params: `Params$Resource$Resources$Buildings$Insert`, options: BodyResponseCallback<`Schema$Building`>, callback: BodyResponseCallback<`Schema$Building`>)
    open fun insert(params: `Params$Resource$Resources$Buildings$Insert`, callback: BodyResponseCallback<`Schema$Building`>)
    open fun insert(callback: BodyResponseCallback<`Schema$Building`>)
    open fun list(params: `Params$Resource$Resources$Buildings$List`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Buildings`>
    open fun list(params: `Params$Resource$Resources$Buildings$List`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Buildings`>)
    open fun list(params: `Params$Resource$Resources$Buildings$List`, options: BodyResponseCallback<`Schema$Buildings`>, callback: BodyResponseCallback<`Schema$Buildings`>)
    open fun list(params: `Params$Resource$Resources$Buildings$List`, callback: BodyResponseCallback<`Schema$Buildings`>)
    open fun list(callback: BodyResponseCallback<`Schema$Buildings`>)
    open fun patch(params: `Params$Resource$Resources$Buildings$Patch`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Building`>
    open fun patch(params: `Params$Resource$Resources$Buildings$Patch`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Building`>)
    open fun patch(params: `Params$Resource$Resources$Buildings$Patch`, options: BodyResponseCallback<`Schema$Building`>, callback: BodyResponseCallback<`Schema$Building`>)
    open fun patch(params: `Params$Resource$Resources$Buildings$Patch`, callback: BodyResponseCallback<`Schema$Building`>)
    open fun patch(callback: BodyResponseCallback<`Schema$Building`>)
    open fun update(params: `Params$Resource$Resources$Buildings$Update`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Building`>
    open fun update(params: `Params$Resource$Resources$Buildings$Update`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Building`>)
    open fun update(params: `Params$Resource$Resources$Buildings$Update`, options: BodyResponseCallback<`Schema$Building`>, callback: BodyResponseCallback<`Schema$Building`>)
    open fun update(params: `Params$Resource$Resources$Buildings$Update`, callback: BodyResponseCallback<`Schema$Building`>)
    open fun update(callback: BodyResponseCallback<`Schema$Building`>)
}

external interface `Params$Resource$Resources$Buildings$Delete` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var buildingId: String?
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Resources$Buildings$Get` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var buildingId: String?
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Resources$Buildings$Insert` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var coordinatesSource: String?
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Building`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Resources$Buildings$List` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var maxResults: Number?
        get() = definedExternally
        set(value) = definedExternally
    var pageToken: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Resources$Buildings$Patch` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var buildingId: String?
        get() = definedExternally
        set(value) = definedExternally
    var coordinatesSource: String?
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Building`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Resources$Buildings$Update` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var buildingId: String?
        get() = definedExternally
        set(value) = definedExternally
    var coordinatesSource: String?
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Building`?
        get() = definedExternally
        set(value) = definedExternally
}

open external class `Resource$Resources$Calendars`(context: APIRequestContext) {
    open var context: APIRequestContext
    open fun delete(params: `Params$Resource$Resources$Calendars$Delete`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun delete(params: `Params$Resource$Resources$Calendars$Delete`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Resources$Calendars$Delete`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Resources$Calendars$Delete`, callback: BodyResponseCallback<Unit>)
    open fun delete(callback: BodyResponseCallback<Unit>)
    open fun get(params: `Params$Resource$Resources$Calendars$Get`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$CalendarResource`>
    open fun get(params: `Params$Resource$Resources$Calendars$Get`, options: MethodOptions, callback: BodyResponseCallback<`Schema$CalendarResource`>)
    open fun get(params: `Params$Resource$Resources$Calendars$Get`, options: BodyResponseCallback<`Schema$CalendarResource`>, callback: BodyResponseCallback<`Schema$CalendarResource`>)
    open fun get(params: `Params$Resource$Resources$Calendars$Get`, callback: BodyResponseCallback<`Schema$CalendarResource`>)
    open fun get(callback: BodyResponseCallback<`Schema$CalendarResource`>)
    open fun insert(params: `Params$Resource$Resources$Calendars$Insert`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$CalendarResource`>
    open fun insert(params: `Params$Resource$Resources$Calendars$Insert`, options: MethodOptions, callback: BodyResponseCallback<`Schema$CalendarResource`>)
    open fun insert(params: `Params$Resource$Resources$Calendars$Insert`, options: BodyResponseCallback<`Schema$CalendarResource`>, callback: BodyResponseCallback<`Schema$CalendarResource`>)
    open fun insert(params: `Params$Resource$Resources$Calendars$Insert`, callback: BodyResponseCallback<`Schema$CalendarResource`>)
    open fun insert(callback: BodyResponseCallback<`Schema$CalendarResource`>)
    open fun list(params: `Params$Resource$Resources$Calendars$List`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$CalendarResources`>
    open fun list(params: `Params$Resource$Resources$Calendars$List`, options: MethodOptions, callback: BodyResponseCallback<`Schema$CalendarResources`>)
    open fun list(params: `Params$Resource$Resources$Calendars$List`, options: BodyResponseCallback<`Schema$CalendarResources`>, callback: BodyResponseCallback<`Schema$CalendarResources`>)
    open fun list(params: `Params$Resource$Resources$Calendars$List`, callback: BodyResponseCallback<`Schema$CalendarResources`>)
    open fun list(callback: BodyResponseCallback<`Schema$CalendarResources`>)
    open fun patch(params: `Params$Resource$Resources$Calendars$Patch`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$CalendarResource`>
    open fun patch(params: `Params$Resource$Resources$Calendars$Patch`, options: MethodOptions, callback: BodyResponseCallback<`Schema$CalendarResource`>)
    open fun patch(params: `Params$Resource$Resources$Calendars$Patch`, options: BodyResponseCallback<`Schema$CalendarResource`>, callback: BodyResponseCallback<`Schema$CalendarResource`>)
    open fun patch(params: `Params$Resource$Resources$Calendars$Patch`, callback: BodyResponseCallback<`Schema$CalendarResource`>)
    open fun patch(callback: BodyResponseCallback<`Schema$CalendarResource`>)
    open fun update(params: `Params$Resource$Resources$Calendars$Update`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$CalendarResource`>
    open fun update(params: `Params$Resource$Resources$Calendars$Update`, options: MethodOptions, callback: BodyResponseCallback<`Schema$CalendarResource`>)
    open fun update(params: `Params$Resource$Resources$Calendars$Update`, options: BodyResponseCallback<`Schema$CalendarResource`>, callback: BodyResponseCallback<`Schema$CalendarResource`>)
    open fun update(params: `Params$Resource$Resources$Calendars$Update`, callback: BodyResponseCallback<`Schema$CalendarResource`>)
    open fun update(callback: BodyResponseCallback<`Schema$CalendarResource`>)
}

external interface `Params$Resource$Resources$Calendars$Delete` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var calendarResourceId: String?
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Resources$Calendars$Get` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var calendarResourceId: String?
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Resources$Calendars$Insert` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$CalendarResource`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Resources$Calendars$List` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var maxResults: Number?
        get() = definedExternally
        set(value) = definedExternally
    var orderBy: String?
        get() = definedExternally
        set(value) = definedExternally
    var pageToken: String?
        get() = definedExternally
        set(value) = definedExternally
    var query: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Resources$Calendars$Patch` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var calendarResourceId: String?
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$CalendarResource`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Resources$Calendars$Update` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var calendarResourceId: String?
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$CalendarResource`?
        get() = definedExternally
        set(value) = definedExternally
}

open external class `Resource$Resources$Features`(context: APIRequestContext) {
    open var context: APIRequestContext
    open fun delete(params: `Params$Resource$Resources$Features$Delete`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun delete(params: `Params$Resource$Resources$Features$Delete`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Resources$Features$Delete`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Resources$Features$Delete`, callback: BodyResponseCallback<Unit>)
    open fun delete(callback: BodyResponseCallback<Unit>)
    open fun get(params: `Params$Resource$Resources$Features$Get`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Feature`>
    open fun get(params: `Params$Resource$Resources$Features$Get`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Feature`>)
    open fun get(params: `Params$Resource$Resources$Features$Get`, options: BodyResponseCallback<`Schema$Feature`>, callback: BodyResponseCallback<`Schema$Feature`>)
    open fun get(params: `Params$Resource$Resources$Features$Get`, callback: BodyResponseCallback<`Schema$Feature`>)
    open fun get(callback: BodyResponseCallback<`Schema$Feature`>)
    open fun insert(params: `Params$Resource$Resources$Features$Insert`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Feature`>
    open fun insert(params: `Params$Resource$Resources$Features$Insert`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Feature`>)
    open fun insert(params: `Params$Resource$Resources$Features$Insert`, options: BodyResponseCallback<`Schema$Feature`>, callback: BodyResponseCallback<`Schema$Feature`>)
    open fun insert(params: `Params$Resource$Resources$Features$Insert`, callback: BodyResponseCallback<`Schema$Feature`>)
    open fun insert(callback: BodyResponseCallback<`Schema$Feature`>)
    open fun list(params: `Params$Resource$Resources$Features$List`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Features`>
    open fun list(params: `Params$Resource$Resources$Features$List`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Features`>)
    open fun list(params: `Params$Resource$Resources$Features$List`, options: BodyResponseCallback<`Schema$Features`>, callback: BodyResponseCallback<`Schema$Features`>)
    open fun list(params: `Params$Resource$Resources$Features$List`, callback: BodyResponseCallback<`Schema$Features`>)
    open fun list(callback: BodyResponseCallback<`Schema$Features`>)
    open fun patch(params: `Params$Resource$Resources$Features$Patch`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Feature`>
    open fun patch(params: `Params$Resource$Resources$Features$Patch`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Feature`>)
    open fun patch(params: `Params$Resource$Resources$Features$Patch`, options: BodyResponseCallback<`Schema$Feature`>, callback: BodyResponseCallback<`Schema$Feature`>)
    open fun patch(params: `Params$Resource$Resources$Features$Patch`, callback: BodyResponseCallback<`Schema$Feature`>)
    open fun patch(callback: BodyResponseCallback<`Schema$Feature`>)
    open fun rename(params: `Params$Resource$Resources$Features$Rename`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun rename(params: `Params$Resource$Resources$Features$Rename`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun rename(params: `Params$Resource$Resources$Features$Rename`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun rename(params: `Params$Resource$Resources$Features$Rename`, callback: BodyResponseCallback<Unit>)
    open fun rename(callback: BodyResponseCallback<Unit>)
    open fun update(params: `Params$Resource$Resources$Features$Update`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Feature`>
    open fun update(params: `Params$Resource$Resources$Features$Update`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Feature`>)
    open fun update(params: `Params$Resource$Resources$Features$Update`, options: BodyResponseCallback<`Schema$Feature`>, callback: BodyResponseCallback<`Schema$Feature`>)
    open fun update(params: `Params$Resource$Resources$Features$Update`, callback: BodyResponseCallback<`Schema$Feature`>)
    open fun update(callback: BodyResponseCallback<`Schema$Feature`>)
}

external interface `Params$Resource$Resources$Features$Delete` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var featureKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Resources$Features$Get` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var featureKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Resources$Features$Insert` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Feature`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Resources$Features$List` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var maxResults: Number?
        get() = definedExternally
        set(value) = definedExternally
    var pageToken: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Resources$Features$Patch` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var featureKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Feature`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Resources$Features$Rename` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var oldName: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$FeatureRename`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Resources$Features$Update` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var featureKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Feature`?
        get() = definedExternally
        set(value) = definedExternally
}

open external class `Resource$Roleassignments`(context: APIRequestContext) {
    open var context: APIRequestContext
    open fun delete(params: `Params$Resource$Roleassignments$Delete`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun delete(params: `Params$Resource$Roleassignments$Delete`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Roleassignments$Delete`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Roleassignments$Delete`, callback: BodyResponseCallback<Unit>)
    open fun delete(callback: BodyResponseCallback<Unit>)
    open fun get(params: `Params$Resource$Roleassignments$Get`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$RoleAssignment`>
    open fun get(params: `Params$Resource$Roleassignments$Get`, options: MethodOptions, callback: BodyResponseCallback<`Schema$RoleAssignment`>)
    open fun get(params: `Params$Resource$Roleassignments$Get`, options: BodyResponseCallback<`Schema$RoleAssignment`>, callback: BodyResponseCallback<`Schema$RoleAssignment`>)
    open fun get(params: `Params$Resource$Roleassignments$Get`, callback: BodyResponseCallback<`Schema$RoleAssignment`>)
    open fun get(callback: BodyResponseCallback<`Schema$RoleAssignment`>)
    open fun insert(params: `Params$Resource$Roleassignments$Insert`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$RoleAssignment`>
    open fun insert(params: `Params$Resource$Roleassignments$Insert`, options: MethodOptions, callback: BodyResponseCallback<`Schema$RoleAssignment`>)
    open fun insert(params: `Params$Resource$Roleassignments$Insert`, options: BodyResponseCallback<`Schema$RoleAssignment`>, callback: BodyResponseCallback<`Schema$RoleAssignment`>)
    open fun insert(params: `Params$Resource$Roleassignments$Insert`, callback: BodyResponseCallback<`Schema$RoleAssignment`>)
    open fun insert(callback: BodyResponseCallback<`Schema$RoleAssignment`>)
    open fun list(params: `Params$Resource$Roleassignments$List`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$RoleAssignments`>
    open fun list(params: `Params$Resource$Roleassignments$List`, options: MethodOptions, callback: BodyResponseCallback<`Schema$RoleAssignments`>)
    open fun list(params: `Params$Resource$Roleassignments$List`, options: BodyResponseCallback<`Schema$RoleAssignments`>, callback: BodyResponseCallback<`Schema$RoleAssignments`>)
    open fun list(params: `Params$Resource$Roleassignments$List`, callback: BodyResponseCallback<`Schema$RoleAssignments`>)
    open fun list(callback: BodyResponseCallback<`Schema$RoleAssignments`>)
}

external interface `Params$Resource$Roleassignments$Delete` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var roleAssignmentId: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Roleassignments$Get` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var roleAssignmentId: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Roleassignments$Insert` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$RoleAssignment`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Roleassignments$List` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var maxResults: Number?
        get() = definedExternally
        set(value) = definedExternally
    var pageToken: String?
        get() = definedExternally
        set(value) = definedExternally
    var roleId: String?
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

open external class `Resource$Roles`(context: APIRequestContext) {
    open var context: APIRequestContext
    open fun delete(params: `Params$Resource$Roles$Delete`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun delete(params: `Params$Resource$Roles$Delete`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Roles$Delete`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Roles$Delete`, callback: BodyResponseCallback<Unit>)
    open fun delete(callback: BodyResponseCallback<Unit>)
    open fun get(params: `Params$Resource$Roles$Get`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Role`>
    open fun get(params: `Params$Resource$Roles$Get`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Role`>)
    open fun get(params: `Params$Resource$Roles$Get`, options: BodyResponseCallback<`Schema$Role`>, callback: BodyResponseCallback<`Schema$Role`>)
    open fun get(params: `Params$Resource$Roles$Get`, callback: BodyResponseCallback<`Schema$Role`>)
    open fun get(callback: BodyResponseCallback<`Schema$Role`>)
    open fun insert(params: `Params$Resource$Roles$Insert`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Role`>
    open fun insert(params: `Params$Resource$Roles$Insert`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Role`>)
    open fun insert(params: `Params$Resource$Roles$Insert`, options: BodyResponseCallback<`Schema$Role`>, callback: BodyResponseCallback<`Schema$Role`>)
    open fun insert(params: `Params$Resource$Roles$Insert`, callback: BodyResponseCallback<`Schema$Role`>)
    open fun insert(callback: BodyResponseCallback<`Schema$Role`>)
    open fun list(params: `Params$Resource$Roles$List`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Roles`>
    open fun list(params: `Params$Resource$Roles$List`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Roles`>)
    open fun list(params: `Params$Resource$Roles$List`, options: BodyResponseCallback<`Schema$Roles`>, callback: BodyResponseCallback<`Schema$Roles`>)
    open fun list(params: `Params$Resource$Roles$List`, callback: BodyResponseCallback<`Schema$Roles`>)
    open fun list(callback: BodyResponseCallback<`Schema$Roles`>)
    open fun patch(params: `Params$Resource$Roles$Patch`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Role`>
    open fun patch(params: `Params$Resource$Roles$Patch`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Role`>)
    open fun patch(params: `Params$Resource$Roles$Patch`, options: BodyResponseCallback<`Schema$Role`>, callback: BodyResponseCallback<`Schema$Role`>)
    open fun patch(params: `Params$Resource$Roles$Patch`, callback: BodyResponseCallback<`Schema$Role`>)
    open fun patch(callback: BodyResponseCallback<`Schema$Role`>)
    open fun update(params: `Params$Resource$Roles$Update`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Role`>
    open fun update(params: `Params$Resource$Roles$Update`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Role`>)
    open fun update(params: `Params$Resource$Roles$Update`, options: BodyResponseCallback<`Schema$Role`>, callback: BodyResponseCallback<`Schema$Role`>)
    open fun update(params: `Params$Resource$Roles$Update`, callback: BodyResponseCallback<`Schema$Role`>)
    open fun update(callback: BodyResponseCallback<`Schema$Role`>)
}

external interface `Params$Resource$Roles$Delete` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var roleId: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Roles$Get` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var roleId: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Roles$Insert` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Role`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Roles$List` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var maxResults: Number?
        get() = definedExternally
        set(value) = definedExternally
    var pageToken: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Roles$Patch` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var roleId: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Role`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Roles$Update` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var roleId: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Role`?
        get() = definedExternally
        set(value) = definedExternally
}

open external class `Resource$Schemas`(context: APIRequestContext) {
    open var context: APIRequestContext
    open fun delete(params: `Params$Resource$Schemas$Delete`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun delete(params: `Params$Resource$Schemas$Delete`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Schemas$Delete`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Schemas$Delete`, callback: BodyResponseCallback<Unit>)
    open fun delete(callback: BodyResponseCallback<Unit>)
    open fun get(params: `Params$Resource$Schemas$Get`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Schema`>
    open fun get(params: `Params$Resource$Schemas$Get`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Schema`>)
    open fun get(params: `Params$Resource$Schemas$Get`, options: BodyResponseCallback<`Schema$Schema`>, callback: BodyResponseCallback<`Schema$Schema`>)
    open fun get(params: `Params$Resource$Schemas$Get`, callback: BodyResponseCallback<`Schema$Schema`>)
    open fun get(callback: BodyResponseCallback<`Schema$Schema`>)
    open fun insert(params: `Params$Resource$Schemas$Insert`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Schema`>
    open fun insert(params: `Params$Resource$Schemas$Insert`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Schema`>)
    open fun insert(params: `Params$Resource$Schemas$Insert`, options: BodyResponseCallback<`Schema$Schema`>, callback: BodyResponseCallback<`Schema$Schema`>)
    open fun insert(params: `Params$Resource$Schemas$Insert`, callback: BodyResponseCallback<`Schema$Schema`>)
    open fun insert(callback: BodyResponseCallback<`Schema$Schema`>)
    open fun list(params: `Params$Resource$Schemas$List`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Schemas`>
    open fun list(params: `Params$Resource$Schemas$List`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Schemas`>)
    open fun list(params: `Params$Resource$Schemas$List`, options: BodyResponseCallback<`Schema$Schemas`>, callback: BodyResponseCallback<`Schema$Schemas`>)
    open fun list(params: `Params$Resource$Schemas$List`, callback: BodyResponseCallback<`Schema$Schemas`>)
    open fun list(callback: BodyResponseCallback<`Schema$Schemas`>)
    open fun patch(params: `Params$Resource$Schemas$Patch`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Schema`>
    open fun patch(params: `Params$Resource$Schemas$Patch`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Schema`>)
    open fun patch(params: `Params$Resource$Schemas$Patch`, options: BodyResponseCallback<`Schema$Schema`>, callback: BodyResponseCallback<`Schema$Schema`>)
    open fun patch(params: `Params$Resource$Schemas$Patch`, callback: BodyResponseCallback<`Schema$Schema`>)
    open fun patch(callback: BodyResponseCallback<`Schema$Schema`>)
    open fun update(params: `Params$Resource$Schemas$Update`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Schema`>
    open fun update(params: `Params$Resource$Schemas$Update`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Schema`>)
    open fun update(params: `Params$Resource$Schemas$Update`, options: BodyResponseCallback<`Schema$Schema`>, callback: BodyResponseCallback<`Schema$Schema`>)
    open fun update(params: `Params$Resource$Schemas$Update`, callback: BodyResponseCallback<`Schema$Schema`>)
    open fun update(callback: BodyResponseCallback<`Schema$Schema`>)
}

external interface `Params$Resource$Schemas$Delete` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customerId: String?
        get() = definedExternally
        set(value) = definedExternally
    var schemaKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Schemas$Get` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customerId: String?
        get() = definedExternally
        set(value) = definedExternally
    var schemaKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Schemas$Insert` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customerId: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Schema`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Schemas$List` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customerId: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Schemas$Patch` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customerId: String?
        get() = definedExternally
        set(value) = definedExternally
    var schemaKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Schema`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Schemas$Update` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customerId: String?
        get() = definedExternally
        set(value) = definedExternally
    var schemaKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Schema`?
        get() = definedExternally
        set(value) = definedExternally
}

open external class `Resource$Tokens`(context: APIRequestContext) {
    open var context: APIRequestContext
    open fun delete(params: `Params$Resource$Tokens$Delete`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun delete(params: `Params$Resource$Tokens$Delete`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Tokens$Delete`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Tokens$Delete`, callback: BodyResponseCallback<Unit>)
    open fun delete(callback: BodyResponseCallback<Unit>)
    open fun get(params: `Params$Resource$Tokens$Get`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Token`>
    open fun get(params: `Params$Resource$Tokens$Get`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Token`>)
    open fun get(params: `Params$Resource$Tokens$Get`, options: BodyResponseCallback<`Schema$Token`>, callback: BodyResponseCallback<`Schema$Token`>)
    open fun get(params: `Params$Resource$Tokens$Get`, callback: BodyResponseCallback<`Schema$Token`>)
    open fun get(callback: BodyResponseCallback<`Schema$Token`>)
    open fun list(params: `Params$Resource$Tokens$List`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Tokens`>
    open fun list(params: `Params$Resource$Tokens$List`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Tokens`>)
    open fun list(params: `Params$Resource$Tokens$List`, options: BodyResponseCallback<`Schema$Tokens`>, callback: BodyResponseCallback<`Schema$Tokens`>)
    open fun list(params: `Params$Resource$Tokens$List`, callback: BodyResponseCallback<`Schema$Tokens`>)
    open fun list(callback: BodyResponseCallback<`Schema$Tokens`>)
}

external interface `Params$Resource$Tokens$Delete` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var clientId: String?
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Tokens$Get` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var clientId: String?
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Tokens$List` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

open external class `Resource$Users`(context: APIRequestContext) {
    open var context: APIRequestContext
    open var aliases: `Resource$Users$Aliases`
    open var photos: `Resource$Users$Photos`
    open fun delete(params: `Params$Resource$Users$Delete`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun delete(params: `Params$Resource$Users$Delete`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Users$Delete`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Users$Delete`, callback: BodyResponseCallback<Unit>)
    open fun delete(callback: BodyResponseCallback<Unit>)
    open fun get(params: `Params$Resource$Users$Get`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$User`>
    open fun get(params: `Params$Resource$Users$Get`, options: MethodOptions, callback: BodyResponseCallback<`Schema$User`>)
    open fun get(params: `Params$Resource$Users$Get`, options: BodyResponseCallback<`Schema$User`>, callback: BodyResponseCallback<`Schema$User`>)
    open fun get(params: `Params$Resource$Users$Get`, callback: BodyResponseCallback<`Schema$User`>)
    open fun get(callback: BodyResponseCallback<`Schema$User`>)
    open fun insert(params: `Params$Resource$Users$Insert`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$User`>
    open fun insert(params: `Params$Resource$Users$Insert`, options: MethodOptions, callback: BodyResponseCallback<`Schema$User`>)
    open fun insert(params: `Params$Resource$Users$Insert`, options: BodyResponseCallback<`Schema$User`>, callback: BodyResponseCallback<`Schema$User`>)
    open fun insert(params: `Params$Resource$Users$Insert`, callback: BodyResponseCallback<`Schema$User`>)
    open fun insert(callback: BodyResponseCallback<`Schema$User`>)
    open fun list(params: Any? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Users`>
    open fun list(params: `Params$Resource$Users$List`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Users`>)
    open fun list(params: `Params$Resource$Users$List`, options: BodyResponseCallback<`Schema$Users`>, callback: BodyResponseCallback<`Schema$Users`>)
    open fun list(params: `Params$Resource$Users$List`, callback: BodyResponseCallback<`Schema$Users`>)
    open fun list(callback: BodyResponseCallback<`Schema$Users`>)
    open fun makeAdmin(params: `Params$Resource$Users$Makeadmin`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun makeAdmin(params: `Params$Resource$Users$Makeadmin`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun makeAdmin(params: `Params$Resource$Users$Makeadmin`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun makeAdmin(params: `Params$Resource$Users$Makeadmin`, callback: BodyResponseCallback<Unit>)
    open fun makeAdmin(callback: BodyResponseCallback<Unit>)
    open fun patch(params: `Params$Resource$Users$Patch`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$User`>
    open fun patch(params: `Params$Resource$Users$Patch`, options: MethodOptions, callback: BodyResponseCallback<`Schema$User`>)
    open fun patch(params: `Params$Resource$Users$Patch`, options: BodyResponseCallback<`Schema$User`>, callback: BodyResponseCallback<`Schema$User`>)
    open fun patch(params: `Params$Resource$Users$Patch`, callback: BodyResponseCallback<`Schema$User`>)
    open fun patch(callback: BodyResponseCallback<`Schema$User`>)
    open fun undelete(params: `Params$Resource$Users$Undelete`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun undelete(params: `Params$Resource$Users$Undelete`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun undelete(params: `Params$Resource$Users$Undelete`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun undelete(params: `Params$Resource$Users$Undelete`, callback: BodyResponseCallback<Unit>)
    open fun undelete(callback: BodyResponseCallback<Unit>)
    open fun update(params: `Params$Resource$Users$Update`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$User`>
    open fun update(params: `Params$Resource$Users$Update`, options: MethodOptions, callback: BodyResponseCallback<`Schema$User`>)
    open fun update(params: `Params$Resource$Users$Update`, options: BodyResponseCallback<`Schema$User`>, callback: BodyResponseCallback<`Schema$User`>)
    open fun update(params: `Params$Resource$Users$Update`, callback: BodyResponseCallback<`Schema$User`>)
    open fun update(callback: BodyResponseCallback<`Schema$User`>)
    open fun watch(params: `Params$Resource$Users$Watch`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Channel`>
    open fun watch(params: `Params$Resource$Users$Watch`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Channel`>)
    open fun watch(params: `Params$Resource$Users$Watch`, options: BodyResponseCallback<`Schema$Channel`>, callback: BodyResponseCallback<`Schema$Channel`>)
    open fun watch(params: `Params$Resource$Users$Watch`, callback: BodyResponseCallback<`Schema$Channel`>)
    open fun watch(callback: BodyResponseCallback<`Schema$Channel`>)
}

external interface `Params$Resource$Users$Delete` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Users$Get` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customFieldMask: String?
        get() = definedExternally
        set(value) = definedExternally
    var projection: String?
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var viewType: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Users$Insert` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$User`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Users$List` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var customFieldMask: String?
        get() = definedExternally
        set(value) = definedExternally
    var domain: String?
        get() = definedExternally
        set(value) = definedExternally
    var event: String?
        get() = definedExternally
        set(value) = definedExternally
    var maxResults: Number?
        get() = definedExternally
        set(value) = definedExternally
    var orderBy: String?
        get() = definedExternally
        set(value) = definedExternally
    var pageToken: String?
        get() = definedExternally
        set(value) = definedExternally
    var projection: String?
        get() = definedExternally
        set(value) = definedExternally
    var query: String?
        get() = definedExternally
        set(value) = definedExternally
    var showDeleted: String?
        get() = definedExternally
        set(value) = definedExternally
    var sortOrder: String?
        get() = definedExternally
        set(value) = definedExternally
    var viewType: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Users$Makeadmin` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$UserMakeAdmin`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Users$Patch` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$User`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Users$Undelete` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$UserUndelete`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Users$Update` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$User`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Users$Watch` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var customer: String?
        get() = definedExternally
        set(value) = definedExternally
    var customFieldMask: String?
        get() = definedExternally
        set(value) = definedExternally
    var domain: String?
        get() = definedExternally
        set(value) = definedExternally
    var event: String?
        get() = definedExternally
        set(value) = definedExternally
    var maxResults: Number?
        get() = definedExternally
        set(value) = definedExternally
    var orderBy: String?
        get() = definedExternally
        set(value) = definedExternally
    var pageToken: String?
        get() = definedExternally
        set(value) = definedExternally
    var projection: String?
        get() = definedExternally
        set(value) = definedExternally
    var query: String?
        get() = definedExternally
        set(value) = definedExternally
    var showDeleted: String?
        get() = definedExternally
        set(value) = definedExternally
    var sortOrder: String?
        get() = definedExternally
        set(value) = definedExternally
    var viewType: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Channel`?
        get() = definedExternally
        set(value) = definedExternally
}

open external class `Resource$Users$Aliases`(context: APIRequestContext) {
    open var context: APIRequestContext
    open fun delete(params: `Params$Resource$Users$Aliases$Delete`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun delete(params: `Params$Resource$Users$Aliases$Delete`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Users$Aliases$Delete`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Users$Aliases$Delete`, callback: BodyResponseCallback<Unit>)
    open fun delete(callback: BodyResponseCallback<Unit>)
    open fun insert(params: `Params$Resource$Users$Aliases$Insert`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Alias`>
    open fun insert(params: `Params$Resource$Users$Aliases$Insert`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Alias`>)
    open fun insert(params: `Params$Resource$Users$Aliases$Insert`, options: BodyResponseCallback<`Schema$Alias`>, callback: BodyResponseCallback<`Schema$Alias`>)
    open fun insert(params: `Params$Resource$Users$Aliases$Insert`, callback: BodyResponseCallback<`Schema$Alias`>)
    open fun insert(callback: BodyResponseCallback<`Schema$Alias`>)
    open fun list(params: `Params$Resource$Users$Aliases$List`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Aliases`>
    open fun list(params: `Params$Resource$Users$Aliases$List`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Aliases`>)
    open fun list(params: `Params$Resource$Users$Aliases$List`, options: BodyResponseCallback<`Schema$Aliases`>, callback: BodyResponseCallback<`Schema$Aliases`>)
    open fun list(params: `Params$Resource$Users$Aliases$List`, callback: BodyResponseCallback<`Schema$Aliases`>)
    open fun list(callback: BodyResponseCallback<`Schema$Aliases`>)
    open fun watch(params: `Params$Resource$Users$Aliases$Watch`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$Channel`>
    open fun watch(params: `Params$Resource$Users$Aliases$Watch`, options: MethodOptions, callback: BodyResponseCallback<`Schema$Channel`>)
    open fun watch(params: `Params$Resource$Users$Aliases$Watch`, options: BodyResponseCallback<`Schema$Channel`>, callback: BodyResponseCallback<`Schema$Channel`>)
    open fun watch(params: `Params$Resource$Users$Aliases$Watch`, callback: BodyResponseCallback<`Schema$Channel`>)
    open fun watch(callback: BodyResponseCallback<`Schema$Channel`>)
}

external interface `Params$Resource$Users$Aliases$Delete` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var alias: String?
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Users$Aliases$Insert` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Alias`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Users$Aliases$List` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var event: String?
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Users$Aliases$Watch` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var event: String?
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$Channel`?
        get() = definedExternally
        set(value) = definedExternally
}

open external class `Resource$Users$Photos`(context: APIRequestContext) {
    open var context: APIRequestContext
    open fun delete(params: `Params$Resource$Users$Photos$Delete`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun delete(params: `Params$Resource$Users$Photos$Delete`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Users$Photos$Delete`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun delete(params: `Params$Resource$Users$Photos$Delete`, callback: BodyResponseCallback<Unit>)
    open fun delete(callback: BodyResponseCallback<Unit>)
    open fun get(params: `Params$Resource$Users$Photos$Get`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$UserPhoto`>
    open fun get(params: `Params$Resource$Users$Photos$Get`, options: MethodOptions, callback: BodyResponseCallback<`Schema$UserPhoto`>)
    open fun get(params: `Params$Resource$Users$Photos$Get`, options: BodyResponseCallback<`Schema$UserPhoto`>, callback: BodyResponseCallback<`Schema$UserPhoto`>)
    open fun get(params: `Params$Resource$Users$Photos$Get`, callback: BodyResponseCallback<`Schema$UserPhoto`>)
    open fun get(callback: BodyResponseCallback<`Schema$UserPhoto`>)
    open fun patch(params: `Params$Resource$Users$Photos$Patch`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$UserPhoto`>
    open fun patch(params: `Params$Resource$Users$Photos$Patch`, options: MethodOptions, callback: BodyResponseCallback<`Schema$UserPhoto`>)
    open fun patch(params: `Params$Resource$Users$Photos$Patch`, options: BodyResponseCallback<`Schema$UserPhoto`>, callback: BodyResponseCallback<`Schema$UserPhoto`>)
    open fun patch(params: `Params$Resource$Users$Photos$Patch`, callback: BodyResponseCallback<`Schema$UserPhoto`>)
    open fun patch(callback: BodyResponseCallback<`Schema$UserPhoto`>)
    open fun update(params: `Params$Resource$Users$Photos$Update`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$UserPhoto`>
    open fun update(params: `Params$Resource$Users$Photos$Update`, options: MethodOptions, callback: BodyResponseCallback<`Schema$UserPhoto`>)
    open fun update(params: `Params$Resource$Users$Photos$Update`, options: BodyResponseCallback<`Schema$UserPhoto`>, callback: BodyResponseCallback<`Schema$UserPhoto`>)
    open fun update(params: `Params$Resource$Users$Photos$Update`, callback: BodyResponseCallback<`Schema$UserPhoto`>)
    open fun update(callback: BodyResponseCallback<`Schema$UserPhoto`>)
}

external interface `Params$Resource$Users$Photos$Delete` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Users$Photos$Get` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Users$Photos$Patch` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$UserPhoto`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Users$Photos$Update` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: `Schema$UserPhoto`?
        get() = definedExternally
        set(value) = definedExternally
}

open external class `Resource$Verificationcodes`(context: APIRequestContext) {
    open var context: APIRequestContext
    open fun generate(params: `Params$Resource$Verificationcodes$Generate`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun generate(params: `Params$Resource$Verificationcodes$Generate`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun generate(params: `Params$Resource$Verificationcodes$Generate`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun generate(params: `Params$Resource$Verificationcodes$Generate`, callback: BodyResponseCallback<Unit>)
    open fun generate(callback: BodyResponseCallback<Unit>)
    open fun invalidate(params: `Params$Resource$Verificationcodes$Invalidate`? = definedExternally, options: MethodOptions? = definedExternally): Promise<Unit>
    open fun invalidate(params: `Params$Resource$Verificationcodes$Invalidate`, options: MethodOptions, callback: BodyResponseCallback<Unit>)
    open fun invalidate(params: `Params$Resource$Verificationcodes$Invalidate`, options: BodyResponseCallback<Unit>, callback: BodyResponseCallback<Unit>)
    open fun invalidate(params: `Params$Resource$Verificationcodes$Invalidate`, callback: BodyResponseCallback<Unit>)
    open fun invalidate(callback: BodyResponseCallback<Unit>)
    open fun list(params: `Params$Resource$Verificationcodes$List`? = definedExternally, options: MethodOptions? = definedExternally): Promise<`Schema$VerificationCodes`>
    open fun list(params: `Params$Resource$Verificationcodes$List`, options: MethodOptions, callback: BodyResponseCallback<`Schema$VerificationCodes`>)
    open fun list(params: `Params$Resource$Verificationcodes$List`, options: BodyResponseCallback<`Schema$VerificationCodes`>, callback: BodyResponseCallback<`Schema$VerificationCodes`>)
    open fun list(params: `Params$Resource$Verificationcodes$List`, callback: BodyResponseCallback<`Schema$VerificationCodes`>)
    open fun list(callback: BodyResponseCallback<`Schema$VerificationCodes`>)
}

external interface `Params$Resource$Verificationcodes$Generate` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Verificationcodes$Invalidate` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `Params$Resource$Verificationcodes$List` : StandardParameters {
    var auth: dynamic /* String | OAuth2Client | JWT | Compute | UserRefreshClient */
        get() = definedExternally
        set(value) = definedExternally
    var userKey: String?
        get() = definedExternally
        set(value) = definedExternally
}
