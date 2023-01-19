package dev.southcity.helicopter

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.ScreenUtils

class HelicopterMoveGame : ApplicationAdapter() {
    companion object {
        const val SCREEN_MARGIN = 100f
    }

    lateinit var font: BitmapFont
    lateinit var batch: SpriteBatch
    lateinit var sprite: Sprite
    lateinit var position: Vector2

    override fun create() {
        batch = SpriteBatch()
        font = BitmapFont()
        font.data.scale(2f)

        sprite = Sprite(Texture("ship.png"))
        sprite.scale(8f)

        position = Vector2(
            Gdx.graphics.width / 2f - sprite.width / 2,
            Gdx.graphics.height / 2f - sprite.height / 2,
        )
    }

    override fun render() {
        ScreenUtils.clear(0f, 0f, 0f, 1f)

        if (Gdx.input.isTouched) {
            val directionToFinger = Vector2(
                Gdx.input.x.toFloat() - position.x,
                (Gdx.graphics.height - Gdx.input.y.toFloat()) - position.y
            )

            if (directionToFinger.len() > 5f) {
                sprite.setOriginCenter()
                sprite.rotation = directionToFinger.angleDeg() - 90

                position.add(directionToFinger.setLength(16f))
            }
        }

        position.x = position.x.coerceIn(SCREEN_MARGIN, Gdx.graphics.width - sprite.width - SCREEN_MARGIN)
        position.y = position.y.coerceIn(SCREEN_MARGIN, Gdx.graphics.height - sprite.height - SCREEN_MARGIN)
        sprite.setPosition(position.x, position.y)

        batch.begin()
        sprite.draw(batch)
        font.draw(batch, position.toString(), SCREEN_MARGIN, Gdx.graphics.height - SCREEN_MARGIN)
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        font.dispose()
        sprite.texture.dispose()
    }
}