package advent.day12

import kotlin.test.*


class Day12Test {
    @Test
    fun `maps letters to heights`() {
        expect(1) { 'a'.toHeight() }
        expect(13) { 'm'.toHeight() }
        expect(26) { 'z'.toHeight() }
    }

    @Test
    fun `maps start and end letters`() {
        expect(1) { 'S'.toHeight() }
        expect(26) { 'E'.toHeight() }
    }

    @Test
    fun `maps unknown letters to a big number`() {
        expect(Integer.MAX_VALUE) { ' '.toHeight() }
    }

    @Test
    fun `parses map`() {
        // given
        val heightMap = """
            aSde
            jmzE
            wxyr
        """.trimIndent()

        // when
        val routeMap = RouteMap.parseMap(heightMap)

        // then
        assertEquals(Elevation(Position(0, 1), 1), routeMap.start)
        assertEquals(Elevation(Position(1, 3), 26), routeMap.target)

        assertEquals(1, routeMap.heightAt(0, 0))
        assertEquals(10, routeMap.heightAt(1, 0))
        assertEquals(24, routeMap.heightAt(2, 1))
        assertEquals(18, routeMap.heightAt(2, 3))
    }

    private val sampleHeightMap = """
        Sabqponm
        abcryxxl
        accszExk
        acctuvwj
        abdefghi
    """.trimIndent()

    @Test
    fun `finds surrounding positions`() {
        val routeMap = RouteMap.parseMap(sampleHeightMap)

        expect(
            setOf(
                Elevation(Position(1, 3), 18),
                Elevation(Position(2, 2), 3),
                Elevation(Position(2, 4), 26),
                Elevation(Position(3, 3), 20),
            ), "in the middle") {
            routeMap.neighbours(Position(2, 3))
        }

        expect(
            setOf(
                Elevation(Position(0, 1), 1),
                Elevation(Position(0, 3), 17),
                Elevation(Position(1, 2), 3),
            ), "at the top") {
            routeMap.neighbours(Position(0, 2))
        }

        expect(
            setOf(
                Elevation(Position(4, 1), 2),
                Elevation(Position(4, 3), 5),
                Elevation(Position(3, 2), 3),
            ), "at the bottom") {
            routeMap.neighbours(Position(4, 2))
        }

        expect(
            setOf(
                Elevation(Position(2, 0), 1),
                Elevation(Position(3, 1), 3),
                Elevation(Position(4, 0), 1),
            ), "on the left") {
            routeMap.neighbours(Position(3, 0))
        }

        expect(
            setOf(
                Elevation(Position(2, 7), 11),
                Elevation(Position(3, 6), 23),
                Elevation(Position(4, 7), 9),
            ), "on the right") {
            routeMap.neighbours(Position(3, 7))
        }

        expect(
            setOf(
                Elevation(Position(3, 7), 10),
                Elevation(Position(4, 6), 8),
            ),
            "in a corner",
        ) {
            routeMap.neighbours(Position(4, 7))
        }
    }

    @Test
    fun `finds shortest route`() {
        val routeMap = RouteMap.parseMap(sampleHeightMap)

        assertEquals(31, routeMap.shortestRoute())
    }

    @Test
    fun `finds shortest route for given elevation`() {
        val routeMap = RouteMap.parseMap(sampleHeightMap)

        println(routeMap.target)
        val shortest = routeMap.shortestRouteFromHeight(1)

        assertEquals(29, shortest)
    }
}
