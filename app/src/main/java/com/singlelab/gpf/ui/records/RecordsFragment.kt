package com.singlelab.gpf.ui.records

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.singlelab.gpf.R
import com.singlelab.gpf.base.BaseFragment
import com.singlelab.gpf.model.event.Event
import com.singlelab.gpf.model.event.EventStatus
import com.singlelab.gpf.model.event.EventType
import com.singlelab.gpf.model.profile.Person
import com.singlelab.gpf.model.profile.Profile
import com.singlelab.gpf.ui.my_profile.MyProfilePresenter
import com.singlelab.gpf.ui.swiper_event.SwiperEventPresenter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_records.name20481
import kotlinx.android.synthetic.main.fragment_records.name20482
import kotlinx.android.synthetic.main.fragment_records.name20483
import kotlinx.android.synthetic.main.fragment_records.nameCats1
import kotlinx.android.synthetic.main.fragment_records.nameCats2
import kotlinx.android.synthetic.main.fragment_records.nameCats3
import kotlinx.android.synthetic.main.fragment_records.nameTetris1
import kotlinx.android.synthetic.main.fragment_records.nameTetris2
import kotlinx.android.synthetic.main.fragment_records.nameTetris3
import kotlinx.android.synthetic.main.fragment_records.nameTiles1
import kotlinx.android.synthetic.main.fragment_records.nameTiles2
import kotlinx.android.synthetic.main.fragment_records.nameTiles3
import kotlinx.android.synthetic.main.fragment_records.record20481
import kotlinx.android.synthetic.main.fragment_records.record20482
import kotlinx.android.synthetic.main.fragment_records.record20483
import kotlinx.android.synthetic.main.fragment_records.recordFlappyCats1
import kotlinx.android.synthetic.main.fragment_records.recordFlappyCats2
import kotlinx.android.synthetic.main.fragment_records.recordFlappyCats3
import kotlinx.android.synthetic.main.fragment_records.recordTetris1
import kotlinx.android.synthetic.main.fragment_records.recordTetris2
import kotlinx.android.synthetic.main.fragment_records.recordTetris3
import kotlinx.android.synthetic.main.fragment_records.recordTetrisall
import kotlinx.android.synthetic.main.fragment_records.recordTiles
import kotlinx.android.synthetic.main.fragment_records.recordTiles1
import kotlinx.android.synthetic.main.fragment_records.recordTiles2
import kotlinx.android.synthetic.main.fragment_records.recordTiles3
import kotlinx.android.synthetic.main.item_card_event.record2048
import kotlinx.android.synthetic.main.item_card_event.recordFlappyCats

@AndroidEntryPoint
class RecordsFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_records, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val copyEvents = SwiperEventPresenter.events.toMutableList()
        copyEvents.add(getEventFromProfile(MyProfilePresenter.profile!!))
        val best2048 = copyEvents.sortedByDescending { it.record2048 }
        val bestFlappyCats = copyEvents.sortedByDescending { it.recordFlappyCat }
        val bestPianoTiles = copyEvents.sortedByDescending { it.recordPiano }
        val bestTetris = copyEvents.sortedByDescending { it.recordTetris }

        record2048.text = MyProfilePresenter.profile!!.personRecord2048.toString()
        recordFlappyCats.text = MyProfilePresenter.profile!!.personRecordCats.toString()
        recordTiles.text = MyProfilePresenter.profile!!.personRecordPiano.toString()
        recordTetrisall.text = MyProfilePresenter.profile!!.personRecordTetris.toString()

        name20481.text = best2048[0].tempName
        name20482.text = best2048[1].tempName
        name20483.text = best2048[2].tempName
        record20481.text = best2048[0].record2048.toString()
        record20482.text = best2048[1].record2048.toString()
        record20483.text = best2048[2].record2048.toString()

        nameCats1.text = bestFlappyCats[0].tempName
        nameCats2.text = bestFlappyCats[1].tempName
        nameCats3.text = bestFlappyCats[2].tempName
        recordFlappyCats1.text = bestFlappyCats[0].recordFlappyCat.toString()
        recordFlappyCats2.text = bestFlappyCats[1].recordFlappyCat.toString()
        recordFlappyCats3.text = bestFlappyCats[2].recordFlappyCat.toString()

        nameTiles1.text = bestPianoTiles[0].tempName
        nameTiles2.text = bestPianoTiles[1].tempName
        nameTiles3.text = bestPianoTiles[2].tempName
        recordTiles1.text = bestPianoTiles[0].recordPiano.toString()
        recordTiles2.text = bestPianoTiles[1].recordPiano.toString()
        recordTiles3.text = bestPianoTiles[2].recordPiano.toString()


        nameTetris1.text = bestTetris[0].tempName
        nameTetris2.text = bestTetris[1].tempName
        nameTetris3.text = bestTetris[2].tempName
        recordTetris1.text = bestTetris[0].recordTetris.toString()
        recordTetris2.text = bestTetris[1].recordTetris.toString()
        recordTetris3.text = bestTetris[2].recordTetris.toString()
//        game.setOnClickListener {
//            findNavController().navigate(R.id.action_games_to_dva48)
//        }
//        game1.setOnClickListener {
//            startActivity(Intent(requireContext(), FlappyCatsHomeActivity::class.java))
//        }
    }

    private fun getEventFromProfile(profile: Profile): Event {
        return Event(
            "0",
            profile.name,
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
            tempName = profile.login.toString(),
            tempGames = listOf("Overwatch"),
            tempCity = "Самара",
            tempDescription = "Игрок Overwatch. Рейтинг Золото 1, 200 часов в игре. Ищу группу для игры в Quick Play и Competitive!",
            tempAge = "24",
            record2048 = profile.personRecord2048,
            recordFlappyCat = profile.personRecordCats,
            recordPiano = profile.personRecordPiano
        )
    }
}