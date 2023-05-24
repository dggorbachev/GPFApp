package com.singlelab.gpf.interactive_games.neon_tetris.piece

import java.util.ArrayList

class PieceU: Piece {

    override var block = "you"

    constructor(axe: Int){
        this.axe = axe
    }

    constructor(pieceList: ArrayList<Piece>, axe: Int){

        this.axe = axe

        this.cube1 = axe + 1
        this.cube5 = axe - 1
        this.cube6 = axe - 9
        this.cube8 = axe - 11

        pieceList[axe] = PieceU(axe)
        pieceList[axe+1] = PieceU(axe + 1)
        pieceList[axe-1] = PieceU(axe - 1)
        pieceList[axe-9] = PieceU(axe - 9)
        pieceList[axe-11] = PieceU(axe - 11)
    }

    override fun rotation(pieceList: ArrayList<Piece>) : Boolean {

        if (!detectWall(pieceList)){
            return false
        }

        this.cube1 = axe + 1
        this.cube2 = axe + 9
        this.cube3 = axe + 10
        this.cube4 = axe + 11
        this.cube5 = axe - 1
        this.cube6 = axe - 9
        this.cube7 = axe - 10
        this.cube8 = axe - 11

        if (this.rotation == 0) {
            if (pieceList[cube3].block == "" &&
                pieceList[cube4].block == "" &&
                pieceList[cube7].block == "" )
            {
                pieceList[cube1] = Piece()
                pieceList[cube5] = Piece()
                pieceList[cube8] = Piece()
                pieceList[cube3] = PieceU(cube3)
                pieceList[cube4] = PieceU(cube4)
                pieceList[cube7] = PieceU(cube7)
                return true
            }
            else {
                this.cube3 = 0
                this.cube4 = 0
                this.cube7 = 0
                return false
            }
        }
        else if (this.rotation == 1) {
            if (pieceList[cube1].block == "" &&
                pieceList[cube2].block == "" &&
                pieceList[cube5].block == "" ) {

                pieceList[cube3] = Piece()
                pieceList[cube6] = Piece()
                pieceList[cube7] = Piece()
                pieceList[cube1] = PieceU(cube1)
                pieceList[cube2] = PieceU(cube2)
                pieceList[cube5] = PieceU(cube5)
                return true
            }
            else {
                this.cube1 = 0
                this.cube2 = 0
                this.cube5 = 0
                return false
            }
        }
        else if (this.rotation == 2) {
            if (pieceList[cube3].block == "" &&
                pieceList[cube7].block == "" &&
                pieceList[cube8].block == "" ) {

                pieceList[cube1] = Piece()
                pieceList[cube4] = Piece()
                pieceList[cube5] = Piece()
                pieceList[cube3] = PieceU(cube3)
                pieceList[cube7] = PieceU(cube7)
                pieceList[cube8] = PieceU(cube8)
                return true
            }
            else {
                this.cube3 = 0
                this.cube7 = 0
                this.cube8 = 0
                return false
            }
        }
        else if (this.rotation == 3) {
            if (pieceList[cube1].block == "" &&
                pieceList[cube5].block == "" &&
                pieceList[cube6].block == "" ) {

                pieceList[cube2] = Piece()
                pieceList[cube3] = Piece()
                pieceList[cube7] = Piece()
                pieceList[cube1] = PieceU(cube1)
                pieceList[cube5] = PieceU(cube5)
                pieceList[cube6] = PieceU(cube6)
                return true
            }
            else {
                this.cube1 = 0
                this.cube5 = 0
                this.cube6 = 0
                return false
            }
        }
        else {
            return false
        }
    }

    override fun detectWall(pieceList: ArrayList<Piece>) : Boolean {

        val rotation = this.rotation

        if (rotation == 1) {
            // detect right wall
            for (x in 9..199 step 10){
                if (this.cube6 == x){
                    return false
                }
            }
        }
        else if (rotation == 3){
            // detect left wall
            for (x in 0..190 step 10){
                if (this.cube8 == x){
                    return false
                }
            }
        }
        else if (rotation == 0) {
            // detect top
            if (this.axe < 10){
                return false
            }
        }
        else if (rotation == 2) {
            // detect bottom
            if (this.axe > 190){
                return false
            }
        }
        return true
    }

    override fun checkRight(pieceList: ArrayList<Piece>) : Boolean {

        val rotation = this.rotation

        if (rotation == 0){
            // detect wall
            for (x in 9..199 step 10){

                if (this.cube1 == x){
                    return false
                }
            }
            // detect block
            if (pieceList[this.cube1 + 1].block == "" && pieceList[this.cube6 + 1].block == "")
            {
                return true
            }
        } else if (rotation == 1) {
            // detect wall
            for (x in 9..199 step 10){

                if (this.cube6 == x){
                    return false
                }
            }
            // detect block
            if (pieceList[this.axe + 1].block == "" && pieceList[this.cube4 + 1].block == "" && pieceList[this.cube6 + 1].block == "")
            {
                return true
            }
        } else if (rotation == 2) {
            // detect wall
            for (x in 9..199 step 10){

                if (this.cube1 == x){
                    return false
                }
            }
            // detect block
            if (pieceList[this.cube1 + 1].block == "" && pieceList[this.cube3 + 1].block == "")
            {
                return true
            }
        } else if (rotation == 3) {
            // detect wall
            for (x in 9..199 step 10){

                if (this.axe == x){
                    return false
                }
            }
            // detect block
            if (pieceList[this.axe + 1].block == "" && pieceList[this.cube3 + 1].block == "" && pieceList[this.cube7 + 1].block == "")
            {
                return true
            }
        }
        return false
    }

    override fun checkLeft(pieceList: ArrayList<Piece>) : Boolean {

        val rotation = this.rotation

        if (rotation == 0){
            // detect wall
            for (x in 0..190 step 10){

                if (this.cube5 == x){
                    return false
                }
            }
            // detect block
            if (pieceList[this.cube5 - 1].block == "" && pieceList[this.cube8 - 1].block == "")
            {
                return true
            }
        } else if (rotation == 1) {
            // detect wall
            for (x in 0..190 step 10){
                if (this.axe == x){
                    return false
                }
            }
            // detect block
            if (pieceList[this.axe - 1].block == "" && pieceList[this.cube3 - 1].block == "" && pieceList[this.cube7 - 1].block == "")
            {
                return true
            }
        } else if (rotation == 2) {
            // detect wall
            for (x in 0..190 step 10){
                if (this.cube2 == x){
                    return false
                }
            }
            // detect block
            if (pieceList[this.cube2 - 1].block == "" && pieceList[this.cube5 - 1].block == "")
            {
                return true
            }
        } else if (rotation == 3) {
            // detect wall
            for (x in 0..190 step 10){
                if (this.cube2 == x){
                    return false
                }
            }
            // detect block
            if (pieceList[this.axe - 1].block == "" && pieceList[this.cube2 - 1].block == "" && pieceList[this.cube8 - 1].block == "")
            {
                return true
            }
        }
        return false
    }

    override fun checkDown(pieceList: ArrayList<Piece>) : Boolean {

        val rotation = this.rotation

        if (rotation == 0){
            if (pieceList[this.axe + 10].block == "" && pieceList[this.cube1 + 10].block == "" && pieceList[this.cube5 + 10].block == "")
            {
                return true
            }
        } else if (rotation == 1) {
            if (pieceList[this.cube3 + 10].block == "" && pieceList[this.cube4 + 10].block == "")
            {
                return true
            }
        } else if (rotation == 2) {
            if (pieceList[this.axe + 10].block == "" && pieceList[this.cube2 + 10].block == "" && pieceList[this.cube4 + 10].block == "")
            {
                return true
            }
        } else if (rotation == 3) {

            if (pieceList[this.cube2 + 10].block == "" && pieceList[this.cube3 + 10].block == "")
            {
                return true
            }
        }
        return false
    }

    override fun moveRight(pieceList: ArrayList<Piece>){

        this.axe += 1
        pieceList[this.axe] = PieceU(this.axe)

        if (this.cube1 != 0) {
            this.cube1 += 1
            pieceList[cube1] = PieceU(cube1)
        }
        if (this.cube2 != 0) {
            this.cube2 += 1
            pieceList[cube2] = PieceU(cube2)
        }
        if (this.cube3 != 0) {
            this.cube3 += 1
            pieceList[cube3] = PieceU(cube3)
        }
        if (this.cube4 != 0) {
            this.cube4 += 1
            pieceList[cube4] = PieceU(cube4)
        }
        if (this.cube5 != 0) {
            this.cube5 += 1
            pieceList[cube5] = PieceU(cube5)
        }
        if (this.cube6 != 0) {
            this.cube6 += 1
            pieceList[cube6] = PieceU(cube6)
        }
        if (this.cube7 != 0) {
            this.cube7 += 1
            pieceList[cube7] = PieceU(cube7)
        }
        if (this.cube8 != 0) {
            this.cube8 += 1
            pieceList[cube8] = PieceU(cube8)
        }
    }

    override fun moveLeft(pieceList: ArrayList<Piece>){

        this.axe -= 1
        pieceList[this.axe] = PieceU(this.axe)

        if (this.cube1 != 0) {
            this.cube1 -= 1
            pieceList[cube1] = PieceU(cube1)
        }
        if (this.cube2 != 0) {
            this.cube2 -= 1
            pieceList[cube2] = PieceU(cube2)
        }
        if (this.cube3 != 0) {
            this.cube3 -= 1
            pieceList[cube3] = PieceU(cube3)
        }
        if (this.cube4 != 0) {
            this.cube4 -= 1
            pieceList[cube4] = PieceU(cube4)
        }
        if (this.cube5 != 0) {
            this.cube5 -= 1
            pieceList[cube5] = PieceU(cube5)
        }
        if (this.cube6 != 0) {
            this.cube6 -= 1
            pieceList[cube6] = PieceU(cube6)
        }
        if (this.cube7 != 0) {
            this.cube7 -= 1
            pieceList[cube7] = PieceU(cube7)
        }
        if (this.cube8 != 0) {
            this.cube8 -= 1
            pieceList[cube8] = PieceU(cube8)
        }
    }

    override fun moveDown(pieceList: ArrayList<Piece>){

        this.axe += 10
        pieceList[this.axe] = PieceU(this.axe)

        if (this.cube1 != 0) {
            this.cube1 += 10
            pieceList[cube1] = PieceU(cube1)
        }
        if (this.cube2 != 0) {
            this.cube2 += 10
            pieceList[cube2] = PieceU(cube2)
        }
        if (this.cube3 != 0) {
            this.cube3 += 10
            pieceList[cube3] = PieceU(cube3)
        }
        if (this.cube4 != 0) {
            this.cube4 += 10
            pieceList[cube4] = PieceU(cube4)
        }
        if (this.cube5 != 0) {
            this.cube5 += 10
            pieceList[cube5] = PieceU(cube5)
        }
        if (this.cube6 != 0) {
            this.cube6 += 10
            pieceList[cube6] = PieceU(cube6)
        }
        if (this.cube7 != 0) {
            this.cube7 += 10
            pieceList[cube7] = PieceU(cube7)
        }
        if (this.cube8 != 0) {
            this.cube8 += 10
            pieceList[cube8] = PieceU(cube8)
        }
    }
}