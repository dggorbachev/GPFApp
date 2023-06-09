package com.singlelab.gpf.model.tutorial

import com.singlelab.gpf.R

enum class TutorialPage(val imageId: Int, val titleId: Int) {
    TUTORIAL_NEW_YEAR(R.drawable.ic_tutorial_new_year, R.string.tutorial_text_new_year),
    TUTORIAL_1(R.drawable.ic_tutorial_1, R.string.tutorial_text_1),
    TUTORIAL_2(R.drawable.ic_tutorial_2, R.string.tutorial_text_2),
    TUTORIAL_3(R.drawable.ic_tutorial_3, R.string.tutorial_text_3),
    TUTORIAL_4(R.drawable.ic_tutorial_4, R.string.tutorial_text_4),
    TUTORIAL_5(R.drawable.ic_tutorial_5, R.string.tutorial_text_5)
}