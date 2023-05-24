package com.singlelab.gpf.interactive_games.neon_tetris.piece

import java.util.ArrayList

class PieceI : Piece {

    override var block: String = "line"

    constructor(axe: Int){
        this.axe = axe
    }

    constructor(pieceList: ArrayList<Piece>, axe: Int){

        this.axe = axe

        this.cube1 = axe - 1
        this.cube2 = axe + 1
        this.cube3 = axe + 2

        pieceList[axe] = PieceI(axe)
        pieceList[axe-1] = PieceI(cube1)
        pieceList[axe+1] = PieceI(cube2)
        pieceList[axe+2] = PieceI(cube3)
    }

    override fun rotation(pieceList: ArrayList<Piece>) : Boolean {

        if (!detectWall(pieceList)){
            return false
        }

        this.cube1 = axe - 1
        this.cube2 = axe + 1
        this.cube3 = axe + 2
        this.cube4 = axe - 10
        this.cube5 = axe + 10
        this.cube6 = axe + 20

        if (this.rotation == 0 || this.rotation == 2) {
            if (pieceList[cube4].block == "" &&
                pieceList[cube5].block == "" &&
                pieceList[cube6].block == "" ) {

                pieceList[cube1] = Piece()
                pieceList[cube2] = Piece()
                pieceList[cube3] = Piece()
                pieceList[cube4] = PieceI(cube4)
                pieceList[cube5] = PieceI(cube5)
                pieceList[cube6] = PieceI(cube6)
                return true
            }
            else {
                this.cube4 = 0
                this.cube5 = 0
                this.cube6 = 0
                return false
            }
        }
        else if (this.rotation == 1 || this.rotation == 3){
            if (pieceList[cube1].block == "" &&
                pieceList[cube2].block == "" &&
                pieceList[cube3].block == "" ) {

                pieceList[cube4] = Piece()
                pieceList[cube5] = Piece()
                pieceList[cube6] = Piece()
                pieceList[cube1] = PieceI(cube1)
                pieceList[cube2] = PieceI(cube2)
                pieceList[cube3] = PieceI(cube3)
                return true
            }
            else {
                this.cube1 = 0
                this.cube2 = 0
                this.cube3 = 0
                return false
            }
        }
        else {
            return false
        }
    }

    override fun detectWall(pieceList: ArrayList<Piece>) : Boolean {

        val rotation = this.rotation

        if (rotation == 1 || rotation == 3) {
            // detect right wall
            for (x in 9..199 step 10){
                if (this.axe == x || this.axe == x-1){
                    return false
                }
            }
            // detect left wall
            for (x in 0..190 step 10){
                if (this.axe == x){
                    return false
                }
            }
        }
        else if (rotation == 0 || rotation == 2) {
            // detect top & bottom
            if (this.axe < 10 || this.axe > 180){
                return false
            }
        }
        return true
    }

    override fun checkRight(pieceList: ArrayList<Piece>) : Boolean {

        val rotation = this.rotation

        if (rotation == 0 || rotation == 2){

            // detect wall
            for (x in 9..199 step 10){
                if (this.cube3 == x){
                    return false
                }
            }
            //detect block
            if (pieceList[this.cube3 + 1].block == "")
            {
                return true
            }

        } else if (rotation == 1 || rotation == 3) {

            // detect wall
            for (x in 9..199 step 10){
                if (this.axe == x){
                    return false
                }
            }
            //detect block
            if (pieceList[this.axe + 1].block == "" && pieceList[this.cube4 + 1].block == "" && pieceList[this.cube5 + 1].block == "" && pieceList[this.cube6 + 1].block == "")
            {
                return true
            }

        }
        return false
    }

    override fun checkLeft(pieceList: ArrayList<Piece>) : Boolean {

        val rotation = this.rotation

        if (rotation == 0 || rotation == 2){

            // detect wall
            for (x in 0..190 step 10){
                if (this.cube1 == x){
                    return false
                }
            }
            // detect block
            if (pieceList[this.cube1 - 1].block == "")
            {
                return true
            }

        } else if (rotation == 1 || rotation == 3) {

            // detect wall
            for (x in 0..190 step 10){
                if (this.axe == x){
                    return false
                }
            }
            // detect block
            if (pieceList[this.axe - 1].block == "" && pieceList[this.cube4 - 1].block == "" && pieceList[this.cube5 - 1].block == "" && pieceList[this.cube6 - 1].block == "")
            {
                return true
            }
        }
        return false
    }

    override fun checkDown(pieceList: ArrayList<Piece>) : Boolean {

        val rotation = this.rotation

        if (rotation == 0 || rotation == 2){

            if (pieceList[this.axe + 10].block == "" && pieceList[this.cube1 + 10].block == "" && pieceList[this.cube2 + 10].block == "" && pieceList[this.cube3 + 10].block == "")
            {
                return true
            }

        } else if (rotation == 1 || rotation == 3) {

            if (pieceList[this.cube6 + 10].block == "")
            {
                return true
            }

        }
        return false
    }

    override fun moveRight(pieceList: ArrayList<Piece>){

        this.axe += 1
        pieceList[this.axe] = PieceI(this.axe)

        if (this.cube1 != 0) {
            this.cube1 += 1
            pieceList[cube1] = PieceI(cube1)
        }
        if (this.cube2 != 0) {
            this.cube2 += 1
            pieceList[cube2] = PieceI(cube2)
        }
        if (this.cube3 != 0) {
            this.cube3 += 1
            pieceList[cube3] = PieceI(cube3)
        }
        if (this.cube4 != 0) {
            this.cube4 += 1
            pieceList[cube4] = PieceI(cube4)
        }
        if (this.cube5 != 0) {
            this.cube5 += 1
            pieceList[cube5] = PieceI(cube5)
        }
        if (this.cube6 != 0) {
            this.cube6 += 1
            pieceList[cube6] = PieceI(cube6)
        }

    }

    override fun moveLeft(pieceList: ArrayList<Piece>){

        this.axe -= 1
        pieceList[this.axe] = PieceI(this.axe)

        if (this.cube1 != 0) {
            this.cube1 -= 1
            pieceList[cube1] = PieceI(cube1)
        }
        if (this.cube2 != 0) {
            this.cube2 -= 1
            pieceList[cube2] = PieceI(cube2)
        }
        if (this.cube3 != 0) {
            this.cube3 -= 1
            pieceList[cube3] = PieceI(cube3)
        }
        if (this.cube4 != 0) {
            this.cube4 -= 1
            pieceList[cube4] = PieceI(cube4)
        }
        if (this.cube5 != 0) {
            this.cube5 -= 1
            pieceList[cube5] = PieceI(cube5)
        }
        if (this.cube6 != 0) {
            this.cube6 -= 1
            pieceList[cube6] = PieceI(cube6)
        }
    }

    override fun moveDown(pieceList: ArrayList<Piece>){

        this.axe += 10
        pieceList[this.axe] = PieceI(this.axe)

        if (this.cube1 != 0) {
            this.cube1 += 10
            pieceList[cube1] = PieceI(cube1)
        }
        if (this.cube2 != 0) {
            this.cube2 += 10
            pieceList[cube2] = PieceI(cube2)
        }
        if (this.cube3 != 0) {
            this.cube3 += 10
            pieceList[cube3] = PieceI(cube3)
        }
        if (this.cube4 != 0) {
            this.cube4 += 10
            pieceList[cube4] = PieceI(cube4)
        }
        if (this.cube5 != 0) {
            this.cube5 += 10
            pieceList[cube5] = PieceI(cube5)
        }
        if (this.cube6 != 0) {
            this.cube6 += 10
            pieceList[cube6] = PieceI(cube6)
        }
    }
}