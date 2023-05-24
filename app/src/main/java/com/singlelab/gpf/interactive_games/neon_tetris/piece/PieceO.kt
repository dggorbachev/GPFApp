package com.singlelab.gpf.interactive_games.neon_tetris.piece

import java.util.ArrayList

class PieceO: Piece {

    override var block = "square"

    constructor(axe: Int){
        this.axe = axe
    }

    constructor(pieceList: ArrayList<Piece>, axe: Int){

        this.axe = axe

        this.cube1 = axe + 1
        this.cube2 = axe + 10
        this.cube3 = axe + 11

        pieceList[axe] = PieceO(axe)
        pieceList[axe+1] = PieceO(axe + 1)
        pieceList[axe+10] = PieceO(axe + 10)
        pieceList[axe+11] = PieceO(axe + 11)

    }

    override fun rotation(pieceList: ArrayList<Piece>) : Boolean {
        // no rotation
        return false
    }

    override fun checkRight(pieceList: ArrayList<Piece>) : Boolean {

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
        return false
    }


    override fun checkLeft(pieceList: ArrayList<Piece>) : Boolean {

        // detect wall
        for (x in 0..190 step 10){

            if (this.axe == x){
                return false
            }
        }
        // detect block
        if (pieceList[this.axe - 1].block == "" && pieceList[this.cube2 - 1].block == "")
        {
            return true
        }
        return false
    }

    override fun checkDown(pieceList: ArrayList<Piece>) : Boolean {

        if (pieceList[this.cube2 + 10].block == "" && pieceList[this.cube3 + 10].block == "")
        {
            return true
        }
        return false
    }


    override fun moveRight(pieceList: ArrayList<Piece>){

        this.axe += 1
        pieceList[this.axe] = PieceO(this.axe)

        if (this.cube1 != 0) {
            this.cube1 += 1
            pieceList[cube1] = PieceO(cube1)
        }
        if (this.cube2 != 0) {
            this.cube2 += 1
            pieceList[cube2] = PieceO(cube2)
        }
        if (this.cube3 != 0) {
            this.cube3 += 1
            pieceList[cube3] = PieceO(cube3)
        }
        if (this.cube4 != 0) {
            this.cube4 += 1
            pieceList[cube4] = PieceO(cube4)
        }

    }

    override fun moveLeft(pieceList: ArrayList<Piece>){

        this.axe -= 1
        pieceList[this.axe] = PieceO(this.axe)

        if (this.cube1 != 0) {
            this.cube1 -= 1
            pieceList[cube1] = PieceO(cube1)
        }
        if (this.cube2 != 0) {
            this.cube2 -= 1
            pieceList[cube2] = PieceO(cube2)
        }
        if (this.cube3 != 0) {
            this.cube3 -= 1
            pieceList[cube3] = PieceO(cube3)
        }
        if (this.cube4 != 0) {
            this.cube4 -= 1
            pieceList[cube4] = PieceO(cube4)
        }
    }

    override fun moveDown(pieceList: ArrayList<Piece>){

        this.axe += 10
        pieceList[this.axe] = PieceO(this.axe)

        if (this.cube1 != 0) {
            this.cube1 += 10
            pieceList[cube1] = PieceO(cube1)
        }
        if (this.cube2 != 0) {
            this.cube2 += 10
            pieceList[cube2] = PieceO(cube2)
        }
        if (this.cube3 != 0) {
            this.cube3 += 10
            pieceList[cube3] = PieceO(cube3)
        }
        if (this.cube4 != 0) {
            this.cube4 += 10
            pieceList[cube4] = PieceO(cube4)
        }
    }

}