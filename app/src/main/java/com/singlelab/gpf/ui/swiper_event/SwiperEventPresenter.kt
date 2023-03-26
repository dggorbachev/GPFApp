package com.singlelab.gpf.ui.swiper_event

import android.util.Log
import com.singlelab.gpf.base.BaseInteractor
import com.singlelab.gpf.base.BasePresenter
import com.singlelab.gpf.model.event.Event
import com.singlelab.gpf.model.event.EventStatus
import com.singlelab.gpf.model.event.EventType
import com.singlelab.gpf.model.event.FilterEvent
import com.singlelab.gpf.model.profile.Person
import com.singlelab.gpf.pref.Preferences
import com.singlelab.gpf.ui.swiper_event.interactor.SwiperEventInteractor
import com.singlelab.net.exceptions.ApiException
import com.singlelab.net.model.auth.AuthData
import kotlinx.coroutines.delay
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class SwiperEventPresenter @Inject constructor(
    private val interactor: SwiperEventInteractor,
    preferences: Preferences?
) : BasePresenter<SwiperEventView>(preferences, interactor as BaseInteractor) {

    var event: Event? = null

    var filterEvent = FilterEvent(cityId = AuthData.cityId, cityName = AuthData.cityName)

    override fun onFirstViewAttach() {
        events = events.shuffled()
        super.onFirstViewAttach()
        loadRandomEvent(true)
//        preferences?.let {
//            if (it.isAfterInstall()) {
//                viewState.showInfoDialog()
//                preferences.setAfterInstall(false)
//            }
//            if (it.isFirstLaunch()) {
//                sendPushToken(preferences.getPushToken())
//            }
//            if (it.getNewYearPromoRewardEnabled()) {
//                viewState.showNewYearImage(true)
//            }
//        }
    }

    private fun sendPushToken(token: String?) {
        token ?: return
        invokeSuspend {
            try {
//                interactor.updatePushToken(token)
                preferences?.setFirstLaunch(false)
            } catch (e: ApiException) {
            }
        }
    }

    fun applyFilter(filterEvent: FilterEvent) {
        if (this.filterEvent != filterEvent) {
            this.filterEvent = filterEvent
            loadRandomEvent()
        }
    }

    fun loadRandomEvent(isFirstAttach: Boolean = false) {

        invokeSuspend {
            if (iterEvent == events.size)
                iterEvent = 0
            this.event = events[iterEvent++]

            runOnMainThread {
                Log.d("events.size", events.size.toString())
                viewState.showEvent(this.event!!)
            }
        }
    }

    fun acceptEvent() {
        invokeSuspend {
            delay(500)
            runOnMainThread {
                loadRandomEvent()
            }
        }
//        val uid = AuthData.uid ?: return
//        event?.eventUid?.let { eventUid ->
//            viewState.showLoading(isShow = true, withoutBackground = true)
//            invokeSuspend {
//                try {
//                    interactor.acceptEvent(
//                        ParticipantRequest(
//                            uid,
//                            eventUid,
//                            if (event!!.isOpenForInvitations) ParticipantStatus.ACTIVE.id else ParticipantStatus.WAITING_FOR_APPROVE_FROM_EVENT.id
//                        )
//                    )
//                    runOnMainThread {
//                        viewState.toAcceptedEvent(event!!.isOpenForInvitations, eventUid)
//                        event = null
//                        viewState.showLoading(isShow = false, withoutBackground = true)
//                    }
//                } catch (e: ApiException) {
//                    runOnMainThread {
//                        viewState.showLoading(isShow = false, withoutBackground = true)
//                        viewState.showError(e.message)
//                    }
//                }
//            }
//        }
    }

    fun rejectEvent() {
        invokeSuspend {
            delay(500)
            runOnMainThread {
                loadRandomEvent()
            }
        }

//        event?.eventUid?.let { eventUid ->
//            viewState.showLoading(isShow = true, withoutBackground = true)
//            invokeSuspend {
//                try {
//                    interactor.rejectEvent(eventUid)
//                    runOnMainThread {
//                        event = null
//                        viewState.showLoading(isShow = false, withoutBackground = true)
//                        loadRandomEvent()
//                    }
//                } catch (e: ApiException) {
//                    runOnMainThread {
//                        viewState.showLoading(isShow = false, withoutBackground = true)
//                        viewState.showError(e.message)
//                    }
//                }
//            }
//        }
    }

    fun sendReport(reasonReport: String) {
        event?.eventUid?.let { uid ->
            viewState.showLoading(true)
            invokeSuspend {
                try {
                    interactor.sendReport(uid, reasonReport)
                    runOnMainThread {
                        viewState.showLoading(false)
                        viewState.showSuccessReport()
                    }
                } catch (e: ApiException) {
                    runOnMainThread {
                        viewState.showLoading(false)
                        viewState.showError(e.message)
                    }
                }
            }
        }
    }

    companion object {

        var iterEvent = 0
        var events = listOf(
            Event(
                "0",
                "Александр",
                18,
                28,
                23.0,
                23.0,
                "Описание",
                "2023-03-23T12:22:10.000",
                "2023-03-26T12:22:10.000",
                "eventResponse.chatUid",
                EventStatus.findById(1),
                listOf(EventType.PARTY),
                mutableListOf(
                    Person(
                        "1",
                        "Вячеслав",
                        "vyach123",
                        "Бросил доту и вам советую",
                        21,
                        "Москва, Россия",
                        "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
                        true,
                        false,
                        false,
                        null
                    ),
                    Person(
                        "2",
                        "Александр",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                listOf(),
                listOf(
                ),
                Person(
                    "2",
                    "admin",
                    "sashaKolom",
                    "Лучший игрок в CS:GO в Митино",
                    20,
                    "Санкт-Петербург, Россия",
                    "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                    true,
                    false,
                    false,
                    null
                ),
                true,
                "123123",
                listOf("https://wonder-day.com/wp-content/uploads/2022/03/wonder-day-avatar-memes-cats-41.jpg"),
                2,
                "Msc",
                false,
                "asd",
                tempImage = "https://wonder-day.com/wp-content/uploads/2022/03/wonder-day-avatar-memes-cats-41.jpg",
                tempName = "Алексей",
                tempGames = listOf("Overwatch"),
                tempCity = "Самара",
                tempDescription = "Игрок Overwatch. Рейтинг Золото 1, 200 часов в игре. Ищу группу для игры в Quick Play и Competitive!",
                tempAge = "24"
            ),
            Event(
                "0",
                "Александр",
                18,
                28,
                23.0,
                23.0,
                "Описание",
                "2023-03-23T12:22:10.000",
                "2023-03-26T12:22:10.000",
                "eventResponse.chatUid",
                EventStatus.findById(1),
                listOf(EventType.PARTY),
                mutableListOf(
                    Person(
                        "1",
                        "Вячеслав",
                        "vyach123",
                        "Бросил доту и вам советую",
                        21,
                        "Москва, Россия",
                        "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
                        true,
                        false,
                        false,
                        null
                    ),
                    Person(
                        "2",
                        "Александр",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                listOf(
                    Person(
                        "2",
                        "Danya",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                listOf(
                    Person(
                        "2",
                        "Misha",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                Person(
                    "2",
                    "admin",
                    "sashaKolom",
                    "Лучший игрок в CS:GO в Митино",
                    20,
                    "Санкт-Петербург, Россия",
                    "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                    true,
                    false,
                    false,
                    null
                ),
                true,
                "123123",
                listOf("https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg"),
                2,
                "Msc",
                false,
                "asd",
                tempImage = "https://klike.net/uploads/posts/2022-06/1654842544_1.jpg",
                tempName = "Александр",
                tempGames = listOf("Dota 2"),
                tempCity = "Санкт-Петербург",
                tempDescription = "Игрок Dota 2. Рейтинг Легионер 5, 800 часов в игре, ищу напарника для игры в Duo Ranked!",
                tempAge = "26"
            ),
            Event(
                "0",
                "Александр",
                18,
                28,
                23.0,
                23.0,
                "Описание",
                "2023-03-23T12:22:10.000",
                "2023-03-26T12:22:10.000",
                "eventResponse.chatUid",
                EventStatus.findById(1),
                listOf(EventType.PARTY),
                mutableListOf(
                    Person(
                        "1",
                        "Вячеслав",
                        "vyach123",
                        "Бросил доту и вам советую",
                        21,
                        "Москва, Россия",
                        "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
                        true,
                        false,
                        false,
                        null
                    ),
                    Person(
                        "2",
                        "Александр",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                listOf(
                    Person(
                        "2",
                        "Danya",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                listOf(
                    Person(
                        "2",
                        "Misha",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                Person(
                    "2",
                    "admin",
                    "sashaKolom",
                    "Лучший игрок в CS:GO в Митино",
                    20,
                    "Санкт-Петербург, Россия",
                    "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                    true,
                    false,
                    false,
                    null
                ),
                true,
                "123123",
                listOf("https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg"),
                2,
                "Msc",
                false,
                "asd",
                tempImage = "https://klike.net/uploads/posts/2022-06/1654842644_4.jpg",
                tempName = "Мария",
                tempGames = listOf("Valorant"),
                tempCity = "Москва",
                tempDescription = "Играю в Valorant. Ранг Золото 2, 300 часов в игре. Ищу команду для игры в Unrated и Competitive!",
                tempAge = "23"
            ),
            Event(
                "0",
                "Александр",
                18,
                28,
                23.0,
                23.0,
                "Описание",
                "2023-03-23T12:22:10.000",
                "2023-03-26T12:22:10.000",
                "eventResponse.chatUid",
                EventStatus.findById(1),
                listOf(EventType.PARTY),
                mutableListOf(
                    Person(
                        "1",
                        "Вячеслав",
                        "vyach123",
                        "Бросил доту и вам советую",
                        21,
                        "Москва, Россия",
                        "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
                        true,
                        false,
                        false,
                        null
                    ),
                    Person(
                        "2",
                        "Александр",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                listOf(
                    Person(
                        "2",
                        "Danya",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                listOf(
                    Person(
                        "2",
                        "Misha",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                Person(
                    "2",
                    "admin",
                    "sashaKolom",
                    "Лучший игрок в CS:GO в Митино",
                    20,
                    "Санкт-Петербург, Россия",
                    "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                    true,
                    false,
                    false,
                    null
                ),
                true,
                "123123",
                listOf("https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg"),
                2,
                "Msc",
                false,
                "asd",
                tempImage = "https://koshka.top/uploads/posts/2021-12/thumbs/1638599322_3-koshka-top-p-kotiki-na-avatarku-3.jpg",
                tempName = "Николай",
                tempGames = listOf("CS:GO"),
                tempCity = "Екатеринбург",
                tempDescription = "Играю в CS:GO. Ранг Золото 3, 500 часов в игре. Ищу напарника для игры в Duo Ranked!",
                tempAge = "27"
            ),
            Event(
                "0",
                "Александр",
                18,
                28,
                23.0,
                23.0,
                "Описание",
                "2023-03-23T12:22:10.000",
                "2023-03-26T12:22:10.000",
                "eventResponse.chatUid",
                EventStatus.findById(1),
                listOf(EventType.PARTY),
                mutableListOf(
                    Person(
                        "1",
                        "Вячеслав",
                        "vyach123",
                        "Бросил доту и вам советую",
                        21,
                        "Москва, Россия",
                        "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
                        true,
                        false,
                        false,
                        null
                    ),
                    Person(
                        "2",
                        "Александр",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                listOf(
                    Person(
                        "2",
                        "Danya",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                listOf(
                    Person(
                        "2",
                        "Misha",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                Person(
                    "2",
                    "admin",
                    "sashaKolom",
                    "Лучший игрок в CS:GO в Митино",
                    20,
                    "Санкт-Петербург, Россия",
                    "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                    true,
                    false,
                    false,
                    null
                ),
                true,
                "123123",
                listOf("https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg"),
                2,
                "Msc",
                false,
                "asd",
                tempImage = "https://coolsen.ru/wp-content/uploads/2021/01/img_600d472398136.jpg",
                tempName = "Максим",
                tempGames = listOf("CS:GO"),
                tempCity = "Киев",
                tempDescription = "Игрок CS:GO. Ранг Мастер Сержант 1, 1500 часов в игре. Ищу группу для игры в режиме Wingman!",
                tempAge = "28"
            ),
            Event(
                "0",
                "Александр",
                18,
                28,
                23.0,
                23.0,
                "Описание",
                "2023-03-23T12:22:10.000",
                "2023-03-26T12:22:10.000",
                "eventResponse.chatUid",
                EventStatus.findById(1),
                listOf(EventType.PARTY),
                mutableListOf(
                    Person(
                        "1",
                        "Вячеслав",
                        "vyach123",
                        "Бросил доту и вам советую",
                        21,
                        "Москва, Россия",
                        "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
                        true,
                        false,
                        false,
                        null
                    ),
                    Person(
                        "2",
                        "Александр",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                listOf(
                    Person(
                        "2",
                        "Danya",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                listOf(
                    Person(
                        "2",
                        "Misha",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                Person(
                    "2",
                    "admin",
                    "sashaKolom",
                    "Лучший игрок в CS:GO в Митино",
                    20,
                    "Санкт-Петербург, Россия",
                    "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                    true,
                    false,
                    false,
                    null
                ),
                true,
                "123123",
                listOf("https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg"),
                2,
                "Msc",
                false,
                "asd",
                tempImage = "https://koshka.top/uploads/posts/2021-12/thumbs/1640007404_57-koshka-top-p-koti-ugar-60.jpg",
                tempName = "Ольга",
                tempGames = listOf("Dota 2"),
                tempCity = "Москва",
                tempDescription = "Игрок Dota 2. Ранг Архонт, 500 часов в игре. Ищу команду для участия в турнирах!",
                tempAge = "23"
            ),
            Event(
                "0",
                "Александр",
                18,
                28,
                23.0,
                23.0,
                "Описание",
                "2023-03-23T12:22:10.000",
                "2023-03-26T12:22:10.000",
                "eventResponse.chatUid",
                EventStatus.findById(1),
                listOf(EventType.PARTY),
                mutableListOf(
                    Person(
                        "1",
                        "Вячеслав",
                        "vyach123",
                        "Бросил доту и вам советую",
                        21,
                        "Москва, Россия",
                        "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
                        true,
                        false,
                        false,
                        null
                    ),
                    Person(
                        "2",
                        "Александр",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                listOf(
                    Person(
                        "2",
                        "Danya",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                listOf(
                    Person(
                        "2",
                        "Misha",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                Person(
                    "2",
                    "admin",
                    "sashaKolom",
                    "Лучший игрок в CS:GO в Митино",
                    20,
                    "Санкт-Петербург, Россия",
                    "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                    true,
                    false,
                    false,
                    null
                ),
                true,
                "123123",
                listOf("https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg"),
                2,
                "Msc",
                false,
                "asd",
                tempImage = "https://sun9-16.userapi.com/impg/tfB3jkEVe2k-R9AHOD4mRFrS4ZbRLOQhF0tU6A/pORhQpkCP7E.jpg?size=863x1080&quality=95&sign=796b0cee53a9115e90e595b31e89259a&c_uniq_tag=wweztZAFJGxC5UiHT6CI1Eb_WzUDabkDWCWZ6WZq3Yc&type=album",
                tempName = "Игорь",
                tempGames = listOf("Valorant"),
                tempCity = "Нижний Новгород",
                tempDescription = "Игрок Valorant. Ранг Радиант, 800 часов в игре. Ищу друзей для игры вместе!",
                tempAge = "25"
            ),
            Event(
                "0",
                "Александр",
                18,
                28,
                23.0,
                23.0,
                "Описание",
                "2023-03-23T12:22:10.000",
                "2023-03-26T12:22:10.000",
                "eventResponse.chatUid",
                EventStatus.findById(1),
                listOf(EventType.PARTY),
                mutableListOf(
                    Person(
                        "1",
                        "Вячеслав",
                        "vyach123",
                        "Бросил доту и вам советую",
                        21,
                        "Москва, Россия",
                        "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
                        true,
                        false,
                        false,
                        null
                    ),
                    Person(
                        "2",
                        "Александр",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                listOf(
                    Person(
                        "2",
                        "Danya",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                listOf(
                    Person(
                        "2",
                        "Misha",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                Person(
                    "2",
                    "admin",
                    "sashaKolom",
                    "Лучший игрок в CS:GO в Митино",
                    20,
                    "Санкт-Петербург, Россия",
                    "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                    true,
                    false,
                    false,
                    null
                ),
                true,
                "123123",
                listOf("https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg"),
                2,
                "Msc",
                false,
                "asd",
                tempImage = "https://static2.tgstat.ru/channels/_0/fe/feb52518964808597402d5c39926ae2d.jpg",
                tempName = "Анна",
                tempGames = listOf("PUBG"),
                tempCity = "Екатеринбург",
                tempDescription = "Игрок PUBG. Ранг Платина, 300 часов в игре. Ищу тиммейтов для игры в Squads!",
                tempAge = "19"
            ),
            Event(
                "0",
                "Александр",
                18,
                28,
                23.0,
                23.0,
                "Описание",
                "2023-03-23T12:22:10.000",
                "2023-03-26T12:22:10.000",
                "eventResponse.chatUid",
                EventStatus.findById(1),
                listOf(EventType.PARTY),
                mutableListOf(
                    Person(
                        "1",
                        "Вячеслав",
                        "vyach123",
                        "Бросил доту и вам советую",
                        21,
                        "Москва, Россия",
                        "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
                        true,
                        false,
                        false,
                        null
                    ),
                    Person(
                        "2",
                        "Александр",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                listOf(
                    Person(
                        "2",
                        "Danya",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                listOf(
                    Person(
                        "2",
                        "Misha",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                Person(
                    "2",
                    "admin",
                    "sashaKolom",
                    "Лучший игрок в CS:GO в Митино",
                    20,
                    "Санкт-Петербург, Россия",
                    "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                    true,
                    false,
                    false,
                    null
                ),
                true,
                "123123",
                listOf("https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg"),
                2,
                "Msc",
                false,
                "asd",
                tempImage = "https://i.pinimg.com/736x/79/3e/11/793e1128b9b35f46db9abb4110e8f4d7.jpg",
                tempName = "Дмитрий",
                tempGames = listOf("Overwatch"),
                tempCity = "Сочи",
                tempDescription = "Игрок Overwatch. Ранг Грандмастер, 1500 часов в игре. Ищу игроков для создания команды!",
                tempAge = "30"
            ),
            Event(
                "0",
                "Александр",
                18,
                28,
                23.0,
                23.0,
                "Описание",
                "2023-03-23T12:22:10.000",
                "2023-03-26T12:22:10.000",
                "eventResponse.chatUid",
                EventStatus.findById(1),
                listOf(EventType.PARTY),
                mutableListOf(
                    Person(
                        "1",
                        "Вячеслав",
                        "vyach123",
                        "Бросил доту и вам советую",
                        21,
                        "Москва, Россия",
                        "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
                        true,
                        false,
                        false,
                        null
                    ),
                    Person(
                        "2",
                        "Александр",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                listOf(
                    Person(
                        "2",
                        "Danya",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                listOf(
                    Person(
                        "2",
                        "Misha",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                Person(
                    "2",
                    "admin",
                    "sashaKolom",
                    "Лучший игрок в CS:GO в Митино",
                    20,
                    "Санкт-Петербург, Россия",
                    "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                    true,
                    false,
                    false,
                    null
                ),
                true,
                "123123",
                listOf("https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg"),
                2,
                "Msc",
                false,
                "asd",
                tempImage = "https://static.wikia.nocookie.net/southpark/images/2/2b/MrKitty.png/revision/latest?cb=20150131115201",
                tempName = "Василиса",
                tempGames = listOf("Diablo 4"),
                tempCity = "Красноярск",
                tempDescription = "Игрок Diablo 4. Играю в жанре ARPG уже несколько лет. Ищу единомышленников для создания клана!",
                tempAge = "26"
            ),
            Event(
                "0",
                "Александр",
                18,
                28,
                23.0,
                23.0,
                "Описание",
                "2023-03-23T12:22:10.000",
                "2023-03-26T12:22:10.000",
                "eventResponse.chatUid",
                EventStatus.findById(1),
                listOf(EventType.PARTY),
                mutableListOf(
                    Person(
                        "1",
                        "Вячеслав",
                        "vyach123",
                        "Бросил доту и вам советую",
                        21,
                        "Москва, Россия",
                        "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
                        true,
                        false,
                        false,
                        null
                    ),
                    Person(
                        "2",
                        "Александр",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                listOf(
                    Person(
                        "2",
                        "Danya",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                listOf(
                    Person(
                        "2",
                        "Misha",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                Person(
                    "2",
                    "admin",
                    "sashaKolom",
                    "Лучший игрок в CS:GO в Митино",
                    20,
                    "Санкт-Петербург, Россия",
                    "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                    true,
                    false,
                    false,
                    null
                ),
                true,
                "123123",
                listOf("https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg"),
                2,
                "Msc",
                false,
                "asd",
                tempName = "Артем",
                tempGames = listOf("CS:GO"),
                tempCity = "Владивосток",
                tempDescription = "Игрок CS:GO. Ранг Легенда, 1000 часов в игре. Ищу тиммейтов для игры в режиме Competitive!",
                tempAge = "27"

            ),
            Event(
                "0",
                "Александр",
                18,
                28,
                23.0,
                23.0,
                "Описание",
                "2023-03-23T12:22:10.000",
                "2023-03-26T12:22:10.000",
                "eventResponse.chatUid",
                EventStatus.findById(1),
                listOf(EventType.PARTY),
                mutableListOf(
                    Person(
                        "1",
                        "Вячеслав",
                        "vyach123",
                        "Бросил доту и вам советую",
                        21,
                        "Москва, Россия",
                        "https://avatars.akamai.steamstatic.com/815bbc83c18710991afed30a18daa3314322f8d0_full.jpg",
                        true,
                        false,
                        false,
                        null
                    ),
                    Person(
                        "2",
                        "Александр",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                listOf(
                    Person(
                        "2",
                        "Danya",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                listOf(
                    Person(
                        "2",
                        "Misha",
                        "sashaKolom",
                        "Лучший игрок в CS:GO в Митино",
                        20,
                        "Санкт-Петербург, Россия",
                        "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                        true,
                        false,
                        false,
                        null
                    )
                ),
                Person(
                    "2",
                    "admin",
                    "sashaKolom",
                    "Лучший игрок в CS:GO в Митино",
                    20,
                    "Санкт-Петербург, Россия",
                    "https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg",
                    true,
                    false,
                    false,
                    null
                ),
                true,
                "123123",
                listOf("https://forum.truckersmp.com/uploads/monthly_2019_06/imported-photo-186659.thumb.jpeg.7ca80c40fa6e972e04cc2f14f5114d80.jpeg"),
                2,
                "Msc",
                false,
                "asd",
                tempImage = "https://avatars.githubusercontent.com/u/13466174?v=4",
                tempName = "Ксения",
                tempGames = listOf("Dota 2"),
                tempCity = "Саратов",
                tempDescription = "Игрок Dota 2. Ранг Легенда 3, 600 часов в игре. Ищу группу для игры в режиме Captain's Mode!",
                tempAge = "21"
            )
        )
    }
}