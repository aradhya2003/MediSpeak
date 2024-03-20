package com.aradhya.MediSpeak.text

import com.google.gson.annotations.SerializedName

data class OpenFdaApiResponse(
    @SerializedName("meta") val meta: Meta,
    @SerializedName("results") val results: List<OpenFdaApiResult>
)

data class Meta(
    @SerializedName("disclaimer") val disclaimer: String,
    @SerializedName("terms") val terms: String,
    @SerializedName("license") val license: String,
    @SerializedName("last_updated") val lastUpdated: String,
    @SerializedName("results") val resultInfo: ResultInfo
)

data class ResultInfo(
    @SerializedName("skip") val skip: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("total") val total: Int
)

data class OpenFdaApiResult(
    @SerializedName("safetyreportversion") val safetyReportVersion: String,
    @SerializedName("safetyreportid") val safetyReportId: String,
    @SerializedName("primarysourcecountry") val primarySourceCountry: String,
    @SerializedName("transmissiondateformat") val transmissionDateFormat: String,
    @SerializedName("transmissiondate") val transmissionDate: String,
    @SerializedName("reporttype") val reportType: String,
    @SerializedName("serious") val serious: String,
    @SerializedName("receivedateformat") val receiveDateFormat: String,
    @SerializedName("receivedate") val receiveDate: String,
    @SerializedName("receiptdateformat") val receiptDateFormat: String,
    @SerializedName("receiptdate") val receiptDate: String,
    @SerializedName("fulfillexpeditecriteria") val fulfilExpediteCriteria: String,
    @SerializedName("primarysource") val primarySource: PrimarySource,
    @SerializedName("sender") val sender: Sender,
    @SerializedName("receiver") val receiver: Receiver,
    @SerializedName("patient") val patient: Patient
)

data class PrimarySource(
    @SerializedName("reportercountry") val reporterCountry: String
)

data class Sender(
    @SerializedName("sendertype") val senderType: String,
    @SerializedName("senderorganization") val senderOrganization: String
)

data class Receiver(
    @SerializedName("receivertype") val receiverType: String,
    @SerializedName("receiverorganization") val receiverOrganization: String
)

data class Patient(
    @SerializedName("patientonsetage") val patientOnsetAge: String,
    @SerializedName("patientonsetageunit") val patientOnsetAgeUnit: String,
    @SerializedName("patientweight") val patientWeight: String,
    @SerializedName("patientsex") val patientSex: String,
    @SerializedName("reaction") val reactions: List<Reaction>,
    @SerializedName("drug") val drugs: List<Drug>
)

data class Reaction(
    @SerializedName("reactionmeddraversionpt") val reactionMedDraVersionPt: String,
    @SerializedName("reactionmeddrapt") val reactionMedDraPt: String
)

data class Drug(
    @SerializedName("drugcharacterization") val drugCharacterization: String?,
    @SerializedName("medicinalproduct") val medicinalProduct: String,
    @SerializedName("drugseparatedosagenumb") val drugSeparateDosageNumb: String?,
    @SerializedName("drugintervaldosageunitnumb") val drugIntervalDosageUnitNumb: String?,
    @SerializedName("drugintervaldosagedefinition") val drugIntervalDosageDefinition: String?,
    @SerializedName("drugadministrationroute") val drugAdministrationRoute: String?,
    @SerializedName("drugindication") val drugIndication: String?,
    @SerializedName("drugadditional") val drugAdditional: String?
)
