package com.singlelab.gpf.model.profile

import android.os.Parcelable
import com.singlelab.net.model.person.ProfileResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
class Profile(
    val personUid: String,
    var login: String? = null,
    var name: String,
    var description: String? = null,
    val cityId: Int,
    var cityName: String,
    var age: Int,
    val imageContentUid: String? = null,
    var isFriend: Boolean = false,
    val friends: List<Person> = arrayListOf(),
    var personRecord2048: Int = 0,
    var personRecordCats: Int = 0,
    var personRecordPiano: Int = 0,
    var password: String? = null
) : Parcelable {
    companion object {
        fun fromResponse(profileResponse: ProfileResponse?): Profile? {
            return if (profileResponse != null) {
                Profile(
                    profileResponse.personUid,
                    profileResponse.login,
                    profileResponse.name,
                    profileResponse.description,
                    profileResponse.cityId,
                    profileResponse.cityName,
                    profileResponse.age,
                    profileResponse.imageContentUid,
                    profileResponse.isFriend,
                    profileResponse.friends.mapNotNull {
                        Person.fromResponse(it)
                    }
                )
            } else {
                null
            }
        }

        fun fromEntity(profileEntity: com.singlelab.gpf.database.entity.Profile?): Profile? {
            return if (profileEntity != null) {
                Profile(
                    profileEntity.personUid,
                    profileEntity.login,
                    profileEntity.name,
                    profileEntity.description,
                    profileEntity.cityId,
                    profileEntity.cityName,
                    profileEntity.age,
                    profileEntity.imageContentUid,
                    profileEntity.isFriend
                )
            } else {
                null
            }
        }
    }

    fun toEntity(): com.singlelab.gpf.database.entity.Profile {
        return com.singlelab.gpf.database.entity.Profile(
            personUid,
            login ?: "",
            name,
            description ?: "",
            cityId,
            cityName,
            age,
            imageContentUid ?: "",
            isFriend
        )
    }
}