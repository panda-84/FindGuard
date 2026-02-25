package com.example.findguard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.findguard.ui.theme.FindGuardTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource

/* ── Colors ─────────────────────────────────────────────────── */
private val DarkBg      = Color(0xFF050A14)
private val NeonBlue    = Color(0xFF00D4FF)
private val NeonGreen   = Color(0xFF00FF88)
private val NeonPurple  = Color(0xFF7B2FFF)
private val GlowBlue    = Color(0x3300D4FF)
private val GlowGreen   = Color(0x3300FF88)

/* ============================================================
   ACTIVITY
   ============================================================ */

class SplashScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FindGuardTheme {
                SplashBody()
            }
        }
    }
}

/* ============================================================
   SPLASH BODY
   ============================================================ */

@Composable
fun SplashBody() {
    val context  = LocalContext.current
    val activity = context as Activity

    // ── Animation states ────────────────────────────────────────
    val logoScale    = remember { Animatable(0f) }
    val logoAlpha    = remember { Animatable(0f) }
    val titleAlpha   = remember { Animatable(0f) }
    val titleOffsetY = remember { Animatable(40f) }
    val taglineAlpha = remember { Animatable(0f) }
    val dotAlpha1    = remember { Animatable(0f) }
    val dotAlpha2    = remember { Animatable(0f) }
    val dotAlpha3    = remember { Animatable(0f) }
    val lineWidth    = remember { Animatable(0f) }

    // ── Infinite rotation for outer ring ────────────────────────
    val infiniteTransition = rememberInfiniteTransition(label = "ring")
    val ringRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue  = 360f,
        animationSpec = infiniteRepeatable(
            animation  = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )
    val ringPulse by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue  = 1f,
        animationSpec = infiniteRepeatable(
            animation  = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue  = 0.8f,
        animationSpec = infiniteRepeatable(
            animation  = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    // ── Orchestrated entrance sequence ──────────────────────────
    LaunchedEffect(Unit) {
        // Logo pops in
        launch {
            logoScale.animateTo(1.1f, tween(500, easing = EaseOutBack))
            logoScale.animateTo(1f,   tween(200, easing = EaseInOutCubic))
        }
        launch { logoAlpha.animateTo(1f, tween(400)) }

        delay(400)

        // Divider line sweeps in
        launch { lineWidth.animateTo(1f, tween(600, easing = EaseOutExpo)) }

        delay(300)

        // Title slides up
        launch { titleAlpha.animateTo(1f,  tween(500)) }
        launch { titleOffsetY.animateTo(0f, tween(500, easing = EaseOutExpo)) }

        delay(300)

        // Tagline fades in
        launch { taglineAlpha.animateTo(1f, tween(500)) }

        delay(400)

        // Loading dots stagger in
        launch { dotAlpha1.animateTo(1f, tween(200)) }
        delay(150)
        launch { dotAlpha2.animateTo(1f, tween(200)) }
        delay(150)
        launch { dotAlpha3.animateTo(1f, tween(200)) }

        // Navigate after total ~2.5s
        delay(1000)
        context.startActivity(Intent(context, LoginActivity::class.java))
        activity.finish()
    }

    // ── UI ───────────────────────────────────────────────────────
    Box(
        modifier         = Modifier
            .fillMaxSize()
            .background(DarkBg),
        contentAlignment = Alignment.Center
    ) {

        // Background grid lines (decorative)
        Canvas(modifier = Modifier.fillMaxSize()) {
            val gridColor = Color(0xFF0D1A2E)
            val step      = 60.dp.toPx()
            var x = 0f
            while (x < size.width) {
                drawLine(gridColor, Offset(x, 0f), Offset(x, size.height), strokeWidth = 1f)
                x += step
            }
            var y = 0f
            while (y < size.height) {
                drawLine(gridColor, Offset(0f, y), Offset(size.width, y), strokeWidth = 1f)
                y += step
            }
        }

        // Ambient glow blobs (bottom layer)
        Box(
            modifier = Modifier
                .size(300.dp)
                .blur(80.dp)
                .background(
                    Brush.radialGradient(
                        listOf(GlowBlue.copy(alpha = glowAlpha * 0.6f), Color.Transparent)
                    ),
                    CircleShape
                )
                .align(Alignment.TopCenter)
        )
        Box(
            modifier = Modifier
                .size(250.dp)
                .blur(70.dp)
                .background(
                    Brush.radialGradient(
                        listOf(GlowGreen.copy(alpha = glowAlpha * 0.4f), Color.Transparent)
                    ),
                    CircleShape
                )
                .align(Alignment.BottomCenter)
        )

        // Main content column
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // ── Logo with animated rings ──────────────────────
            Box(
                contentAlignment = Alignment.Center,
                modifier         = Modifier.size(200.dp)
            ) {
                // Outer spinning ring
                Canvas(
                    modifier = Modifier
                        .size(190.dp)
                        .scale(ringPulse)
                        .alpha(logoAlpha.value)
                ) {
                    rotate(ringRotation) {
                        drawArc(
                            brush       = Brush.sweepGradient(
                                listOf(Color.Transparent, NeonBlue, NeonGreen, Color.Transparent)
                            ),
                            startAngle  = 0f,
                            sweepAngle  = 300f,
                            useCenter   = false,
                            style       = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                        )
                    }
                }

                // Inner static ring
                Canvas(
                    modifier = Modifier
                        .size(160.dp)
                        .alpha(logoAlpha.value)
                ) {
                    drawCircle(
                        color  = NeonBlue.copy(alpha = 0.15f),
                        style  = Stroke(width = 1.dp.toPx())
                    )
                }

                // Glow circle behind logo
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .blur(20.dp)
                        .background(
                            Brush.radialGradient(
                                listOf(NeonBlue.copy(alpha = 0.4f), Color.Transparent)
                            ),
                            CircleShape
                        )
                )

                // Logo image
                Image(
                    painter            = painterResource(R.drawable.logo),
                    contentDescription = "FindGuard Logo",
                    modifier           = Modifier
                        .size(100.dp)
                        .scale(logoScale.value)
                        .alpha(logoAlpha.value)
                )

                // Corner accent dots
                listOf(
                    Alignment.TopStart, Alignment.TopEnd,
                    Alignment.BottomStart, Alignment.BottomEnd
                ).forEach { alignment ->
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .align(alignment)
                            .background(NeonGreen, CircleShape)
                            .alpha(logoAlpha.value)
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // ── Animated divider line ─────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth(lineWidth.value * 0.55f)
                    .height(1.5.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color.Transparent, NeonBlue, NeonGreen, Color.Transparent)
                        )
                    )
            )

            Spacer(Modifier.height(20.dp))

            // ── App name ──────────────────────────────────────
            Text(
                text      = "FINDGUARD",
                fontSize  = 36.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 8.sp,
                color = NeonBlue,
                modifier  = Modifier
                    .alpha(titleAlpha.value)
                    .graphicsLayer { translationY = titleOffsetY.value }
            )
            Spacer(Modifier.height(8.dp))

            // ── Tagline ───────────────────────────────────────
            Text(
                text      = "ELITE SECURITY ACROSS NEPAL",
                fontSize  = 11.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 3.sp,
                color     = Color(0xFF6B8BA4),
                textAlign = TextAlign.Center,
                modifier  = Modifier
                    .alpha(taglineAlpha.value)
                    .padding(horizontal = 32.dp)
            )

            Spacer(Modifier.height(60.dp))

            // ── Loading dots ──────────────────────────────────
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment     = Alignment.CenterVertically
            ) {
                LoadingDot(alpha = dotAlpha1.value, color = NeonBlue)
                LoadingDot(alpha = dotAlpha2.value, color = NeonGreen)
                LoadingDot(alpha = dotAlpha3.value, color = NeonBlue)
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text      = "Securing your world...",
                fontSize  = 12.sp,
                color     = Color(0xFF3D5A73),
                letterSpacing = 1.sp,
                modifier  = Modifier.alpha(taglineAlpha.value)
            )
        }

        // ── Corner decorations ────────────────────────────────
        CornerDecoration(Modifier.align(Alignment.TopStart).padding(24.dp),  true,  true)
        CornerDecoration(Modifier.align(Alignment.TopEnd).padding(24.dp),    false, true)
        CornerDecoration(Modifier.align(Alignment.BottomStart).padding(24.dp), true,  false)
        CornerDecoration(Modifier.align(Alignment.BottomEnd).padding(24.dp),  false, false)

        // ── Version badge ─────────────────────────────────────
        Text(
            text     = "v1.0.0",
            fontSize = 10.sp,
            color    = Color(0xFF1E3A4A),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
                .alpha(taglineAlpha.value)
        )
    }
}

/* ============================================================
   HELPER COMPOSABLES
   ============================================================ */

@Composable
private fun LoadingDot(alpha: Float, color: Color) {
    val infiniteTransition = rememberInfiniteTransition(label = "dot")
    val pulse by infiniteTransition.animateFloat(
        initialValue  = 0.6f,
        targetValue   = 1f,
        animationSpec = infiniteRepeatable(
            animation  = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dotPulse"
    )

    Box(
        modifier = Modifier
            .size(8.dp)
            .scale(pulse)
            .alpha(alpha)
            .background(color, CircleShape)
    )
}

@Composable
private fun CornerDecoration(
    modifier  : Modifier,
    leftSide  : Boolean,
    topSide   : Boolean
) {
    val lineColor = NeonBlue.copy(alpha = 0.4f)
    val size      = 20.dp
    val thickness = 2.dp

    Box(modifier = modifier) {
        // Horizontal bar
        Box(
            modifier = Modifier
                .width(size)
                .height(thickness)
                .align(if (leftSide) Alignment.TopStart else Alignment.TopEnd)
                .background(lineColor, RoundedCornerShape(1.dp))
        )
        // Vertical bar
        Box(
            modifier = Modifier
                .width(thickness)
                .height(size)
                .align(if (leftSide) Alignment.TopStart else Alignment.TopEnd)
                .background(lineColor, RoundedCornerShape(1.dp))
        )
    }
}

/* ============================================================
   PREVIEW
   ============================================================ */

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
    FindGuardTheme {
        SplashBody()
    }
}
