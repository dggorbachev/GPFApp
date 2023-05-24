package com.singlelab.gpf.interactive_games.neon_tetris.piece

import java.util.ArrayList


open class Piece {

    var axe: Int = 0
    open var block: String = ""

    open var rotation = 0

    open var cube1 = 0
    open var cube2 = 0
    open var cube3 = 0
    open var cube4 = 0
    open var cube5 = 0
    open var cube6 = 0
    open var cube7 = 0
    open var cube8 = 0

    open fun rotation(pieceList: ArrayList<Piece>) : Boolean{
        return false
    }

    open fun moveDown(pieceList: ArrayList<Piece>){}

    open fun moveRight(pieceList: ArrayList<Piece>){}

    open fun moveLeft(pieceList: ArrayList<Piece>){}

    open fun checkRight(pieceList: ArrayList<Piece>) : Boolean{
        return false
    }

    open fun checkLeft(pieceList: ArrayList<Piece>) : Boolean{
        return false
    }

    open fun checkDown(pieceList: ArrayList<Piece>) : Boolean {
        return false
    }

    open fun detectWall(pieceList: ArrayList<Piece>) : Boolean {
        return false
    }

    fun removeBlock(pieceList: ArrayList<Piece>){

        pieceList[axe] = Piece()

        if (pieceList[cube1].block != "") {
            pieceList[cube1] = Piece()
        }else {
            cube1 = 0
        }
        if (pieceList[cube2].block != "") {
            pieceList[cube2] = Piece()
        } else {
            cube2 = 0
        }
        if (pieceList[cube3].block != "") {
            pieceList[cube3] = Piece()
        }else {
            cube3 = 0
        }
        if (pieceList[cube4].block != "") {
            pieceList[cube4] = Piece()
        }else {
            cube4 = 0
        }
        if (pieceList[cube5].block != "") {
            pieceList[cube5] = Piece()
        }else {
            cube5 = 0
        }
        if (pieceList[cube6].block != "") {
            pieceList[cube6] = Piece()
        } else {
            cube6 = 0
        }
        if (pieceList[cube7].block != "") {
            pieceList[cube7] = Piece()
        }else {
            cube7 = 0
        }
        if (pieceList[cube8].block != "") {
            pieceList[cube8] = Piece()
        }else {
            cube8 = 0
        }
    }

    fun detectBottom(pieceList: ArrayList<Piece>) : Boolean{

        return try {
            pieceList[this.axe + 10]
            pieceList[this.cube1 + 10]
            pieceList[this.cube2 + 10]
            pieceList[this.cube3 + 10]
            pieceList[this.cube4 + 10]
            pieceList[this.cube5 + 10]
            pieceList[this.cube6 + 10]
            pieceList[this.cube7 + 10]
            pieceList[this.cube8 + 10]
            true
        } catch (exception: IndexOutOfBoundsException){
            false
        }
    }

}