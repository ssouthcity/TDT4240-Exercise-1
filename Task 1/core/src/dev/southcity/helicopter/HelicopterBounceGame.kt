package dev.southcity.helicopter

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.ScreenUtils
import kotlin.random.Random

class HelicopterBounceGame : ApplicationAdapter() {
    lateinit var batch: SpriteBatch
    lateinit var sprite: Sprite
    lateinit var position: Vector2
    lateinit var velocity: Vector2

    override fun create() {
        batch = SpriteBatch()

        sprite = Sprite(Texture("ship.png"))
        sprite.scale(8f)

        position = Vector2(
            Gdx.graphics.width / 2f - sprite.width / 2,
            Gdx.graphics.height / 2f - sprite.height / 2,
        )

        velocity = Vector2(
            Random.nextFloat() - 0.5f,
            Random.nextFloat() - 0.5f,
        ).setLength(16f)
    }

    override fun render() {
        ScreenUtils.clear(0f, 0f, 0f, 1f)

        position.add(velocity)

        if (position.x < 0 || position.x + sprite.width > Gdx.graphics.width) {
            position.x = position.x.coerceIn(0f, Gdx.graphics.width.toFloat())
            velocity.x = -velocity.x
        }

        if (position.y < 0 || position.y + sprite.height > Gdx.graphics.height) {
            position.y = position.y.coerceIn(0f, Gdx.graphics.height.toFloat())
            velocity.y = -velocity.y
        }

        sprite.setPosition(position.x, position.y)

        sprite.setOriginCenter()
        sprite.rotation = velocity.angleDeg() - 90

        batch.begin()
        sprite.draw(batch)
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        sprite.texture.dispose()
    }
}