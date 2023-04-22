package com.singlelab.gpf.interactive_games.flappy_cats.Game

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import com.singlelab.gpf.R
import com.singlelab.gpf.interactive_games.flappy_cats.FlappyCatsHomeActivity
import com.singlelab.gpf.ui.my_profile.MyProfilePresenter
import java.util.*

@SuppressLint("ViewConstructor")
class GameView internal constructor(
    @get:JvmName("getAdapterContext") var context: Context,
    screenWidth: Int,
    screenHeight: Int
) :
    SurfaceView(
        context
    ), Runnable {
    var backgrounds: ArrayList<Background>

    @Volatile
    private var running = false
    private var gameThread: Thread? = null
    private val paint: Paint
    private var canvas: Canvas? = null
    private val rect: Rect
    private val ourHolder: SurfaceHolder
    var fps: Long = 60
    var screenWidth: Int
    var screenHeight: Int
    var cats: Array<Bitmap?>
    var cats_hurt: Array<Bitmap?>
    var click: Array<Bitmap?>
    var fish: Bitmap
    var bomb: Bitmap
    var pause: Bitmap
    var catdead: Bitmap
    var home: Bitmap
    var restart: Bitmap
    var resume: Bitmap
    var health1: Bitmap
    var health2: Bitmap
    var health3: Bitmap
    var scorePaint = Paint()
    var pausePaint = Paint()
    var overPaint = Paint()
    var catPaint = Paint()
    var catFrame = 0
    var clickFrame = 0
    var velocity = 0
    var gravity = 1
    var catX: Int
    var catY: Int
    var gameState = false
    var pregameState = false
    var gameOver = false
    var pregameOver = false
    var addfish = false
    var gamePause = false
    var pregamePause = false
    var postgamOver = false
    var random: Random
    var numberofBombs = 8
    var fishX: Int
    var fishY: Int
    var bombX = IntArray(numberofBombs)
    var bombY = IntArray(numberofBombs)
    var tubeVelocity = 5
    var maxY: Int
    var score = 0
    var speedGame = 1
    var addbomb = 0
    var centerWidth: Int
    var centerHeight: Int
    var health1X: Int
    var health1Y: Int
    var health2X: Int
    var health2Y: Int
    var health3X: Int
    var health3Y: Int
    var clickX: Int
    var clickY: Int
    var hasMenuKey = ViewConfiguration.get(getContext() as Activity).hasPermanentMenuKey()
    var hasBackkey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
    var enviny = 0
    var savescore = false
    override fun run() {
        while (running) {
            val startFrameTime = System.currentTimeMillis()
            if (!gamePause && !postgamOver) {
                update()
                draw()
            }
            val timeThisFrame = System.currentTimeMillis() - startFrameTime
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame
            }
        }
    }

    fun CheckHitItem(scalex: Int, scaley: Int, x: Int, y: Int, width: Int, height: Int): Boolean {
        return (scalex < x && x < (scalex + cats[catFrame]!!.getWidth()) && scaley < y && y < (scaley + cats[catFrame]!!.getHeight()) ||
                scalex < x + width && x + width < (scalex + cats[catFrame]!!.getWidth()) && scaley < y + height && y + height < (scaley + cats[catFrame]!!.getHeight()))
    }

    fun Checkclickitem(scalex: Int, scaley: Int, width: Int, height: Int, x: Int, y: Int): Boolean {
        return scalex <= x && x <= (scalex + width) && scaley <= y && y <= (scaley + height)
    }

    val gestureDetector = GestureDetector(object : SimpleOnGestureListener() {
        override fun onLongPress(e: MotionEvent) {
            if (!pregameOver) {
                longtouch = true
            }
        }
    })
    var longtouch = false
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x.toInt()
        val y = event.y.toInt()
        val action = event.action
        gestureDetector.onTouchEvent(event)
        if (action == MotionEvent.ACTION_DOWN) {
            if (!pregameOver) {
                if (!gameState) {
                    if (!Checkclickitem(
                            screenWidth - pause.width - 20,
                            20,
                            pause.width,
                            pause.height,
                            x, y
                        )
                    ) {
                        pregameState = true
                        velocity = -20
                    }
                } else {
                    if (!gamePause) {
                        if (Checkclickitem(
                                screenWidth - pause.width - 20,
                                20,
                                pause.width,
                                pause.height,
                                x, y
                            )
                        ) {
                            pregamePause = true
                        }
                    } else {
                        if (Checkclickitem(
                                centerWidth - resume.width / 2,
                                centerHeight,
                                resume.width,
                                resume.height,
                                x, y
                            )
                        ) {
                            pregamePause = false
                            gamePause = false
                        }
                        if (Checkclickitem(
                                centerWidth - restart.width / 2,
                                centerHeight + resume.height + 50,
                                restart.width,
                                restart.height,
                                x, y
                            )
                        ) {
                            pause()
                            val intent = Intent(
                                getContext(),
                                StartGame::class.java
                            ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            getContext().startActivity(intent)
                            (getContext() as Activity).overridePendingTransition(
                                R.anim.fade_in_activity,
                                R.anim.fade_out_activity
                            )
                            (getContext() as Activity).finish()
                        }
                        if (Checkclickitem(
                                centerWidth - home.width / 2,
                                centerHeight + resume.height + 50 + home.height + 50,
                                home.width,
                                home.height,
                                x, y
                            )
                        ) {
                            pause()
                            val intent =
                                Intent(getContext(), FlappyCatsHomeActivity::class.java).setFlags(
                                    Intent.FLAG_ACTIVITY_CLEAR_TOP
                                )
                            getContext().startActivity(intent)
                            (getContext() as Activity).overridePendingTransition(
                                R.anim.fade_in_activity,
                                R.anim.fade_out_activity
                            )
                            (getContext() as Activity).finish()
                        }
                    }
                }
                if (!gameOver) {
                    longtouch = true
                }
            } else {
                longtouch = false
                if (gameOver) {
                    if (Checkclickitem(
                            centerWidth - home.width / 2,
                            centerHeight + 100,
                            home.width,
                            home.height,
                            x, y
                        )
                    ) {
                        pause()
                        val intent = Intent(
                            getContext(),
                            FlappyCatsHomeActivity::class.java
                        ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        getContext().startActivity(intent)
                        (getContext() as Activity).overridePendingTransition(
                            R.anim.fade_in_activity,
                            R.anim.fade_out_activity
                        )
                        (getContext() as Activity).finish()
                    }
                    if (Checkclickitem(
                            centerWidth - restart.width / 2,
                            centerHeight + 100 + home.height + 50,
                            restart.width,
                            restart.height,
                            x, y
                        )
                    ) {
                        pause()
                        val intent = Intent(
                            getContext(),
                            StartGame::class.java
                        ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        getContext().startActivity(intent)
                        (getContext() as Activity).overridePendingTransition(
                            R.anim.fade_in_activity,
                            R.anim.fade_out_activity
                        )
                        (getContext() as Activity).finish()
                    }
                }
            }
        }
        if (action == MotionEvent.ACTION_UP) {
            longtouch = false
        }
        return true
    }

    private fun update() {
        for (bg in backgrounds) {
            bg.update(fps)
        }
    }

    var ck = 0
    var c_bg = 0
    var keep_c = score
    var add_bomb = score
    var health = 3
    var framerate = 0
    var savecatY = 0
    var time_hurt = 0
    var isHurt = false
    var godmode = false
    var alpha_mode = 0
    var alpha = 255
    var time_god_mode = 0

    init {
        (getContext() as Activity).window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        if (hasBackkey && hasMenuKey) {
            enviny = 102
        } else {
            val decorView = (getContext() as Activity).window.decorView
            val uiOptions = SYSTEM_UI_FLAG_HIDE_NAVIGATION or SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            decorView.systemUiVisibility = uiOptions
            enviny = 110
        }
        this.screenWidth = screenWidth
        this.screenHeight = screenHeight
        ourHolder = holder
        paint = Paint()
        rect = Rect(0, 0, screenWidth, screenHeight)
        backgrounds = ArrayList()
        backgrounds.add(
            Background(
                context,
                screenWidth,
                screenHeight,
                "bg1", 0, enviny, 70f
            )
        )
        backgrounds.add(
            Background(
                context,
                screenWidth,
                screenHeight,
                "bg2", 0, enviny, 70f
            )
        )
        backgrounds.add(
            Background(
                context,
                screenWidth,
                screenHeight,
                "bg3", 0, enviny, 70f
            )
        )
        backgrounds.add(
            Background(
                context,
                screenWidth,
                screenHeight,
                "bg4", 0, enviny, 0f
            )
        )
        fish = BitmapFactory.decodeResource(resources, R.drawable.fish)
        bomb = BitmapFactory.decodeResource(resources, R.drawable.bomb)
        pause = BitmapFactory.decodeResource(resources, R.drawable.pause)
        catdead = BitmapFactory.decodeResource(resources, R.drawable.catdead)
        home = BitmapFactory.decodeResource(resources, R.drawable.home)
        restart = BitmapFactory.decodeResource(resources, R.drawable.restart)
        resume = BitmapFactory.decodeResource(resources, R.drawable.resume)
        cats = arrayOfNulls(4)
        cats[0] = BitmapFactory.decodeResource(resources, R.drawable.cat1)
        cats[1] = BitmapFactory.decodeResource(resources, R.drawable.cat2)
        cats[2] = BitmapFactory.decodeResource(resources, R.drawable.cat3)
        cats[3] = BitmapFactory.decodeResource(resources, R.drawable.cat4)
        cats_hurt = arrayOfNulls(4)
        cats_hurt[0] = BitmapFactory.decodeResource(resources, R.drawable.cat1_hurt)
        cats_hurt[1] = BitmapFactory.decodeResource(resources, R.drawable.cat2_hurt)
        cats_hurt[2] = BitmapFactory.decodeResource(resources, R.drawable.cat3_hurt)
        cats_hurt[3] = BitmapFactory.decodeResource(resources, R.drawable.cat4_hurt)
        health1 = BitmapFactory.decodeResource(resources, R.drawable.cat2)
        health2 = BitmapFactory.decodeResource(resources, R.drawable.cat1)
        health3 = BitmapFactory.decodeResource(resources, R.drawable.cat4)
        click = arrayOfNulls(2)
        click[0] = BitmapFactory.decodeResource(resources, R.drawable.click1)
        click[1] = BitmapFactory.decodeResource(resources, R.drawable.click2)
        catX = cats[catFrame]!!.getWidth()
        catY = -cats[catFrame]!!.getHeight()
        maxY = screenHeight / 100 * 55 - cats[catFrame]!!.getHeight()
        centerWidth = screenWidth / 2
        centerHeight = screenHeight / 2
        clickX = centerWidth - click[clickFrame]!!.getWidth() / 2
        clickY = centerHeight - click[clickFrame]!!.getHeight() / 2
        scorePaint.color = Color.WHITE
        scorePaint.textSize = 80f
        val plain = Typeface.createFromAsset(getContext().assets, "fonts/ARCADECLASSIC.TTF")
        val bold = Typeface.create(plain, Typeface.NORMAL)
        scorePaint.typeface = bold
        scorePaint.getTextBounds("Score: $score", 0, "Score: $score".length, rect)
        pausePaint.color = Color.WHITE
        pausePaint.textSize = 200f
        pausePaint.typeface = bold
        overPaint.color = Color.BLACK
        overPaint.textSize = 200f
        overPaint.typeface = bold
        health1X = rect.width() + 300
        health1Y = 30
        health2X = rect.width() + 300 + health1.width + 10
        health2Y = 30
        health3X = rect.width() + 300 + health1.width * 2 + 10
        health3Y = 30
        random = Random()
        for (i in 0 until numberofBombs) {
            bombX[i] = screenWidth + random.nextInt(1000) + bomb.height
            bombY[i] = random.nextInt(screenHeight - bomb.height)
        }
        fishX = screenWidth + fish.width
        fishY = random.nextInt(screenHeight - fish.height)
    }

    private fun draw() {
        if (ourHolder.surface.isValid) {
            canvas = ourHolder.lockCanvas()
            if (!gameOver) {
                if (gameState) {
                    if (score == keep_c + 250) {
                        c_bg += 1
                        keep_c = score
                    }
                }
                drawBackground(c_bg % 3)
                if (health > 0 && !pregamePause) {
                    if (gameState || pregameState) {
                        canvas!!.drawText("Score: $score", 50f, 100f, scorePaint)
                    }
                }
                if (!gameState && !pregameState) {
                    if (framerate <= 20) {
                        clickFrame = 0
                        framerate += 1
                    } else if (framerate < 40) {
                        clickFrame = 1
                        framerate += 1
                    } else {
                        framerate = 0
                    }
                    canvas!!.drawBitmap(
                        click[clickFrame]!!,
                        clickX.toFloat(),
                        clickY.toFloat(),
                        paint
                    )
                }
                if (pregameState) {
                    if (catY >= screenHeight / 2 - cats[0]!!.height / 2) {
                        catY = screenHeight / 2 - cats[0]!!.height / 2
                        gameState = true
                        pregameState = false
                    } else {
                        catY += 30
                    }
                }
                if (health >= 1 && !pregamePause) {
                    if (gameState || pregameState) {
                        canvas!!.drawBitmap(health1, health1X.toFloat(), health1Y.toFloat(), paint)
                    }
                }
                if (health >= 2 && !pregamePause) {
                    if (gameState || pregameState) {
                        canvas!!.drawBitmap(health2, health2X.toFloat(), health2Y.toFloat(), paint)
                    }
                }
                if (health == 3 && !pregamePause) {
                    if (gameState || pregameState) {
                        canvas!!.drawBitmap(health3, health3X.toFloat(), health3Y.toFloat(), paint)
                    }
                }
                if (pregamePause) {
                    paint.alpha = 100
                    catPaint.alpha = 100
                } else {
                    paint.alpha = 255
                    catPaint.alpha = 255
                    if (health > 0) {
                        if (gameState || pregameState) {
                            canvas!!.drawBitmap(
                                pause,
                                (screenWidth - pause.width - 20).toFloat(),
                                20f,
                                paint
                            )
                        }
                    }
                }
                if (godmode && health > 0) {
                    if (alpha_mode == 0) {
                        alpha -= 10
                        if (alpha <= 50) {
                            alpha = 50
                            alpha_mode = 1
                        }
                    } else if (alpha_mode == 1) {
                        alpha += 10
                        if (alpha >= 255) {
                            alpha = 255
                            alpha_mode = 0
                        }
                    }
                    catPaint.alpha = alpha
                    time_god_mode += 1
                    if (time_god_mode == 150) {
                        godmode = false
                        isHurt = false
                        time_hurt = 0
                        time_god_mode = 0
                    }
                }
                if (pregameState) {
                    if (catFrame == 0) {
                        catFrame = 1
                    } else if (catFrame == 1) {
                        catFrame = 2
                    } else if (catFrame == 2) {
                        catFrame = 3
                    } else if (catFrame == 3) {
                        catFrame = 0
                    }
                    if (!isHurt || godmode) {
                        canvas!!.drawBitmap(
                            cats[catFrame]!!,
                            catX.toFloat(),
                            catY.toFloat(),
                            catPaint
                        )
                    } else {
                        canvas!!.drawBitmap(
                            cats_hurt[catFrame]!!,
                            catX.toFloat(),
                            catY.toFloat(),
                            catPaint
                        )
                    }
                }
                if (gameState) {
                    if (isHurt) {
                        time_hurt += 1
                        if (time_hurt == 10) {
                            godmode = true
                        }
                    }
                    if (score == add_bomb + 100) {
                        addbomb += 1
                        add_bomb = score
                    }
                    speedGame += 1
                    if (speedGame == 500) {
                        tubeVelocity += 1
                        speedGame = 0
                    }
                    if (!pregameOver) {
                        ck += 1
                        if (ck == 20) {
                            score += 1
                            ck = 0
                        }
                    }
                    if (!longtouch) {
                        velocity += gravity
                        catY += velocity
                    } else {
                        catY -= 20
                        velocity = 0
                    }
                    if (catY <= 0) {
                        catY = 0
                        velocity = 0
                    }
                    if (!pregameOver) {
                        if (catY > screenHeight - cats[catFrame]!!.height) {
                            catY = screenHeight - cats[catFrame]!!.height
                        }
                    } else {
                        if (catY > screenHeight - cats[catFrame]!!.height) {
                            gameOver = true
                            gameState = false
                        }
                    }
                    if (health < 3) {
                        if (!addfish) {
                            val predict = random.nextInt(1000)
                            if (predict == 1) {
                                fishX = screenWidth + fish.width
                                fishY = if (hasBackkey && hasMenuKey) {
                                    random.nextInt(screenHeight - fish.height)
                                } else {
                                    random.nextInt(screenHeight)
                                }
                                addfish = true
                            }
                        }
                        if (addfish) {
                            fishX -= tubeVelocity
                            if (fishX < -fish.width) {
                                addfish = false
                            }
                            if (fishX >= -fish.width) {
                                canvas!!.drawBitmap(fish, fishX.toFloat(), fishY.toFloat(), paint)
                            }
                        }
                    }
                    for (i in 0 until numberofBombs) {
                        bombX[i] -= tubeVelocity
                        if (bombX[i] < -bomb.width) {
                            bombX[i] = screenWidth + random.nextInt(1000) + bomb.width
                            bombY[i] = random.nextInt(screenHeight - bomb.height)
                        }
                        if (CheckHitItem(
                                catX,
                                catY,
                                fishX,
                                fishY,
                                fish.width,
                                fish.height
                            ) && !pregameOver
                        ) {
                            fishX = -1000
                            fishY = -1000
                            if (health < 3) {
                                health += 1
                            }
                        }
                        if (CheckHitItem(
                                catX,
                                catY,
                                bombX[i],
                                bombY[i],
                                bomb.width,
                                bomb.height
                            ) && !pregameOver
                        ) {
                            if (!godmode && !isHurt) {
                                if (health > 0) {
                                    isHurt = true
                                }
                                bombX[i] = -1000
                                bombY[i] = -1000
                                if (health > 0) {
                                    health -= 1
                                }
                                if (health == 0) {
                                    savecatY = catY
                                    velocity = -20
                                    pregameOver = true
                                }
                                if (pregameOver) {
                                    longtouch = false
                                    if (catY <= savecatY - 20) {
                                        catY -= 20
                                    } else {
                                        velocity += gravity
                                        catY += velocity
                                    }
                                }
                            }
                        }
                        if (bombX[i] >= -bomb.width) {
                            canvas!!.drawBitmap(bomb, bombX[i].toFloat(), bombY[i].toFloat(), paint)
                        }
                    }
                    if (catFrame == 0) {
                        catFrame = 1
                    } else if (catFrame == 1) {
                        catFrame = 2
                    } else if (catFrame == 2) {
                        catFrame = 3
                    } else if (catFrame == 3) {
                        catFrame = 0
                    }
                    if (!isHurt || godmode) {
                        canvas!!.drawBitmap(
                            cats[catFrame]!!,
                            catX.toFloat(),
                            catY.toFloat(),
                            catPaint
                        )
                    } else {
                        canvas!!.drawBitmap(
                            cats_hurt[catFrame]!!,
                            catX.toFloat(),
                            catY.toFloat(),
                            catPaint
                        )
                    }
                }
                if (pregamePause) {
                    pausePaint.getTextBounds("PAUSE", 0, "PAUSE".length, rect)
                    canvas!!.drawText(
                        "PAUSE", (centerWidth - rect.width() / 2).toFloat(),
                        (
                                centerHeight - rect.height() * 3).toFloat(),
                        pausePaint
                    )
                    canvas!!.drawBitmap(
                        resume,
                        (centerWidth - resume.width / 2).toFloat(),
                        centerHeight.toFloat(),
                        pausePaint
                    )
                    canvas!!.drawBitmap(
                        restart,
                        (centerWidth - home.width / 2).toFloat(),
                        (centerHeight + resume.height + 50).toFloat(),
                        pausePaint
                    )
                    canvas!!.drawBitmap(
                        home,
                        (centerWidth - restart.width / 2).toFloat(),
                        (centerHeight + resume.height + 50 + home.height + 50).toFloat(),
                        pausePaint
                    )
                    pregamePause = false
                    gamePause = true
                }
            } else {
                if (!savescore) {
                    MyProfilePresenter.profile!!.personRecordCats = score
                }
                drawBackground(3)
                overPaint.getTextBounds("Score: $score", 0, "Score: $score".length, rect)
                canvas!!.drawBitmap(
                    catdead,
                    (centerWidth - catdead.width / 2).toFloat(),
                    (centerHeight - catdead.height * 2).toFloat(),
                    overPaint
                )
                canvas!!.drawText(
                    "Score: $score", (centerWidth - rect.width() / 2).toFloat(),
                    centerHeight.toFloat(),
                    overPaint
                )
                canvas!!.drawBitmap(
                    home,
                    (centerWidth - home.width / 2).toFloat(),
                    (centerHeight + 100).toFloat(),
                    overPaint
                )
                canvas!!.drawBitmap(
                    restart,
                    (centerWidth - restart.width / 2).toFloat(),
                    (centerHeight + 100 + home.height + 50).toFloat(),
                    overPaint
                )
                postgamOver = true
            }

            // Unlock and draw the scene
            ourHolder.unlockCanvasAndPost(canvas!!)
        }
    }

    private fun drawBackground(position: Int) {

        // Make a copy of the relevant background
        val bg = backgrounds[position]

        // define what portion of images to capture and
        // what coordinates of screen to draw them at

        // For the regular bitmap
        val fromRect1 = Rect(0, 0, bg.width - bg.xClip, bg.height)
        val toRect1 = Rect(bg.xClip, bg.startY, bg.width, bg.endY)

        // For the reversed background
        val fromRect2 = Rect(bg.width - bg.xClip, 0, bg.width, bg.height)
        val toRect2 = Rect(0, bg.startY, bg.xClip, bg.endY)

        //draw the two background bitmaps
        if (!bg.reversedFirst) {
            canvas!!!!.drawBitmap(bg.bitmap, fromRect1, toRect1, paint)
            canvas!!!!.drawBitmap(bg.bitmapReversed, fromRect2, toRect2, paint)
        } else {
            canvas!!!!.drawBitmap(bg.bitmap, fromRect2, toRect2, paint)
            canvas!!!!.drawBitmap(bg.bitmapReversed, fromRect1, toRect1, paint)
        }
    }

    // Clean up our thread if the game is stopped
    fun pause() {
        running = false
        try {
            gameThread!!.join()
        } catch (e: InterruptedException) {
            // Error
        }
    }

    // Make a new thread and start it
    // Execution moves to our run method
    fun resume() {
        running = true
        gameThread = Thread(this)
        gameThread!!.start()
    }
}