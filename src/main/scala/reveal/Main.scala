package reveal

import java.io.File
import javax.imageio.ImageIO
import javax.swing.{JComponent, JFrame}
import java.awt.image.BufferedImage
import java.awt._
import me.shadaj.leap._

object Main extends App {
  val screenWidth = 598
  val screenHeight = 448

  val view = new Reveal(screenWidth, screenHeight)
  val frame = new JFrame("LeapReveal")
  frame.getContentPane.add(view)
  frame.getContentPane.setBackground(Color.gray)
  frame.pack()
  frame.setVisible(true)
  frame.setSize(screenWidth, screenHeight)
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
}

class Reveal(screenWidth: Int, screenHeight: Int) extends JComponent {
  val img: BufferedImage = ImageIO.read(new File("scala.jpg"))

  val fingerBlockSize = 20
  val controllerBottomOffset = 100

  LeapFrames().subscribe { frame =>
    try {
      val scaleX = img.getWidth(this).toDouble/screenWidth
      val scaleY = img.getHeight(this).toDouble/screenHeight
      val g = getGraphics.asInstanceOf[Graphics2D]

      frame.fingers.filter(_.isExtended).foreach { f =>
        val x = (f.tipPosition.x * (screenWidth.toDouble/300)).toInt
        val y = (f.tipPosition.y * (screenHeight.toDouble/300)).toInt


        g.drawImage(img,
          (screenWidth/2) + x,
          (screenHeight + controllerBottomOffset) - y,
          (screenWidth/2) + x + fingerBlockSize,
          (screenHeight + controllerBottomOffset) - y + fingerBlockSize,
          (scaleX * ((screenWidth/2) + x)).toInt,
          (scaleY * ((screenHeight + controllerBottomOffset) - y)).toInt,
          (scaleX * ((screenWidth/2) + x + fingerBlockSize)).toInt,
          (scaleY * ((screenHeight + controllerBottomOffset) - y + fingerBlockSize)).toInt,
          this)
      }
    } catch {
      case _: Throwable =>
    }
  }
}