package com.nomadly.app.data.mock

import com.nomadly.app.model.Board
import com.nomadly.app.model.Destination
import com.nomadly.app.model.UserProfile

object MockRepository {

    val destinations: List<Destination> = listOf(
        Destination(
            id = "amalfi-coast",
            name = "Amalfi Coast",
            country = "Italy",
            region = "Campania",
            tagline = "Where cliffs kiss the sea",
            description = "The Amalfi Coast is a 50-kilometre stretch of coastline along the southern edge of the Sorrentine Peninsula. Dramatic cliffs plunge into turquoise waters, while pastel-coloured villages cling impossibly to the rock. This UNESCO World Heritage Site offers some of the most romantic and breathtaking scenery in Europe.",
            imageUrl = "https://picsum.photos/seed/amalfi/800/1200",
            tags = listOf("Coastal", "Romantic", "UNESCO", "Gastronomy"),
            rating = 4.9f,
            reviewCount = 2847,
            highlights = listOf(
                "Drive the iconic SS163 coastal road",
                "Explore the historic town of Ravello",
                "Swim in the emerald Grotta dello Smeraldo",
                "Sample limoncello and fresh seafood",
                "Day trip to the ruins of Pompeii"
            ),
            bestTimeToVisit = "April – June, September – October",
            avgBudget = "\$180–\$320/day",
            isSaved = true
        ),
        Destination(
            id = "santorini",
            name = "Santorini",
            country = "Greece",
            region = "Cyclades",
            tagline = "Sunsets that rewrite the rules",
            description = "Santorini is a volcanic island in the southern Aegean Sea, known for its dramatic caldera views, iconic blue-domed churches, and spectacular sunsets. The island's whitewashed clifftop villages of Oia and Fira offer an otherworldly charm that has captivated travellers for generations.",
            imageUrl = "https://picsum.photos/seed/santorini/800/1200",
            tags = listOf("Island", "Romantic", "Architecture", "Wine"),
            rating = 4.8f,
            reviewCount = 3421,
            highlights = listOf(
                "Watch the sunset from Oia's castle ruins",
                "Explore the ancient site of Akrotiri",
                "Sail around the volcanic caldera",
                "Sample local Assyrtiko white wine",
                "Relax on the unique black and red sand beaches"
            ),
            bestTimeToVisit = "May – June, September",
            avgBudget = "\$200–\$400/day",
            isSaved = false
        ),
        Destination(
            id = "kyoto",
            name = "Kyoto",
            country = "Japan",
            region = "Kansai",
            tagline = "A thousand years of living culture",
            description = "Kyoto served as Japan's imperial capital for over a millennium and remains the country's cultural heart. With over 1,600 Buddhist temples, 400 Shinto shrines, and 17 UNESCO World Heritage Sites, it offers an unparalleled immersion into traditional Japanese aesthetics, ceremony, and philosophy.",
            imageUrl = "https://picsum.photos/seed/kyoto/800/1200",
            tags = listOf("Culture", "Temples", "Gardens", "Gastronomy"),
            rating = 4.9f,
            reviewCount = 4102,
            highlights = listOf(
                "Walk through the Arashiyama bamboo grove at dawn",
                "Explore the thousands of torii gates at Fushimi Inari",
                "Experience a traditional tea ceremony in Higashiyama",
                "Visit Kinkaku-ji, the Golden Pavilion",
                "Stroll through the historic Gion geisha district"
            ),
            bestTimeToVisit = "March – May, October – November",
            avgBudget = "\$120–\$250/day",
            isSaved = true
        ),
        Destination(
            id = "maldives",
            name = "Maldives",
            country = "Maldives",
            region = "South Asia",
            tagline = "The world's last paradise",
            description = "The Maldives is an archipelago of 1,192 coral islands grouped into 26 atolls in the Indian Ocean. Famous for its overwater bungalows, crystal-clear lagoons, and extraordinary marine biodiversity, it represents the pinnacle of tropical luxury. Each island resort is its own private universe.",
            imageUrl = "https://picsum.photos/seed/maldives/800/1200",
            tags = listOf("Luxury", "Diving", "Romance", "Beach"),
            rating = 4.9f,
            reviewCount = 1876,
            highlights = listOf(
                "Stay in an iconic overwater villa",
                "Snorkel or dive with manta rays and whale sharks",
                "Watch bioluminescent plankton glow at night",
                "Enjoy a private sandbank dinner at sunset",
                "Take a seaplane transfer between atolls"
            ),
            bestTimeToVisit = "November – April",
            avgBudget = "\$500–\$1500/day",
            isSaved = false
        ),
        Destination(
            id = "swiss-alps",
            name = "Swiss Alps",
            country = "Switzerland",
            region = "Central Europe",
            tagline = "Where heaven touches earth",
            description = "The Swiss Alps form one of the most iconic mountain landscapes in the world. From the Matterhorn to the Jungfrau, these peaks offer year-round adventure — world-class skiing and snowboarding in winter, hiking and climbing in summer. Charming alpine villages dot the valleys between the giants.",
            imageUrl = "https://picsum.photos/seed/swissalps/800/1200",
            tags = listOf("Adventure", "Skiing", "Hiking", "Nature"),
            rating = 4.8f,
            reviewCount = 2234,
            highlights = listOf(
                "Take the Jungfraujoch railway to Europe's highest station",
                "Ski the legendary runs of Zermatt beneath the Matterhorn",
                "Hike the Haute Route from Chamonix to Zermatt",
                "Visit the chocolate and cheese makers of Gruyères",
                "Experience the thermal baths of Leukerbad"
            ),
            bestTimeToVisit = "December – March, June – September",
            avgBudget = "\$250–\$500/day",
            isSaved = false
        ),
        Destination(
            id = "marrakech",
            name = "Marrakech",
            country = "Morocco",
            region = "North Africa",
            tagline = "A sensory overture in ochre",
            description = "Marrakech is a city of dizzying contrasts and hypnotic beauty. The ancient medina, a UNESCO World Heritage Site, draws you into a labyrinth of souks, riads, and mosques. The legendary Jemaa el-Fna square transforms from a daytime market into a nightly carnival of storytellers, musicians, and food vendors.",
            imageUrl = "https://picsum.photos/seed/marrakech/800/1200",
            tags = listOf("Culture", "Souks", "Architecture", "Gastronomy"),
            rating = 4.7f,
            reviewCount = 2981,
            highlights = listOf(
                "Get lost in the labyrinthine souks of the medina",
                "Stay in a traditional riad with a central courtyard",
                "Visit the Saadian Tombs and Bahia Palace",
                "Day trip to the Atlas Mountains and Berber villages",
                "Explore the serene Majorelle Garden and YSL Museum"
            ),
            bestTimeToVisit = "March – May, October – November",
            avgBudget = "\$80–\$200/day",
            isSaved = true
        ),
        Destination(
            id = "bali",
            name = "Bali",
            country = "Indonesia",
            region = "Southeast Asia",
            tagline = "The island of a thousand temples",
            description = "Bali is Indonesia's most famous island, a place where ancient Hindu temples stand in rice paddies, volcanoes loom over terraced hillsides, and wellness traditions run deep. From the artistic enclave of Ubud to the surf breaks of Uluwatu, Bali offers a profound diversity of experiences in a compact tropical setting.",
            imageUrl = "https://picsum.photos/seed/bali/800/1200",
            tags = listOf("Spirituality", "Surfing", "Wellness", "Nature"),
            rating = 4.7f,
            reviewCount = 5621,
            highlights = listOf(
                "Witness sunrise over Mount Batur's volcanic rim",
                "Attend a traditional Kecak fire dance at Uluwatu",
                "Cycle through the emerald rice terraces of Tegalalang",
                "Join a yoga and meditation retreat in Ubud",
                "Surf the legendary breaks of Uluwatu and Padang Padang"
            ),
            bestTimeToVisit = "April – October",
            avgBudget = "\$60–\$180/day",
            isSaved = false
        ),
        Destination(
            id = "patagonia",
            name = "Patagonia",
            country = "Argentina",
            region = "South America",
            tagline = "The end of the world, the beginning of awe",
            description = "Patagonia stretches across the southern tip of South America, encompassing vast steppes, glaciers, mountains, and fjords across Argentina and Chile. The iconic Torres del Paine and Los Glaciares national parks attract adventurers and nature lovers seeking one of earth's last great wildernesses.",
            imageUrl = "https://picsum.photos/seed/patagonia/800/1200",
            tags = listOf("Adventure", "Hiking", "Wildlife", "Glaciers"),
            rating = 4.9f,
            reviewCount = 1432,
            highlights = listOf(
                "Trek the famous W Circuit in Torres del Paine",
                "Sail to the Perito Moreno Glacier and watch it calve",
                "Spot condors, guanacos, and pumas in the wild",
                "Hike to the base of the iconic Fitz Roy massif",
                "End the road at the mythical city of Ushuaia"
            ),
            bestTimeToVisit = "October – March",
            avgBudget = "\$100–\$250/day",
            isSaved = false
        )
    )

    val boards: List<Board> = listOf(
        Board(
            id = "summer-greece",
            title = "Summer in Greece",
            destinationCount = 7,
            imageUrl = "https://picsum.photos/seed/greece-board/800/1200",
            collaboratorAvatars = listOf(
                "https://picsum.photos/seed/avatar1/100/100",
                "https://picsum.photos/seed/avatar2/100/100",
                "https://picsum.photos/seed/avatar3/100/100"
            ),
            extraCollaborators = 2
        ),
        Board(
            id = "nordic-escapes",
            title = "Nordic Escapes",
            destinationCount = 5,
            imageUrl = "https://picsum.photos/seed/nordic/800/1200",
            collaboratorAvatars = listOf(
                "https://picsum.photos/seed/avatar4/100/100",
                "https://picsum.photos/seed/avatar5/100/100"
            ),
            extraCollaborators = 0
        ),
        Board(
            id = "winter-maldives",
            title = "Winter in Maldives",
            destinationCount = 4,
            imageUrl = "https://picsum.photos/seed/maldives-board/800/1200",
            collaboratorAvatars = listOf(
                "https://picsum.photos/seed/avatar6/100/100",
                "https://picsum.photos/seed/avatar7/100/100",
                "https://picsum.photos/seed/avatar8/100/100"
            ),
            extraCollaborators = 5
        ),
        Board(
            id = "swiss-alps-trek",
            title = "Swiss Alps Trek",
            destinationCount = 6,
            imageUrl = "https://picsum.photos/seed/alps-board/800/1200",
            collaboratorAvatars = listOf(
                "https://picsum.photos/seed/avatar9/100/100"
            ),
            extraCollaborators = 0
        ),
        Board(
            id = "tokyo-kyoto",
            title = "Tokyo & Kyoto",
            destinationCount = 9,
            imageUrl = "https://picsum.photos/seed/japan-board/800/1200",
            collaboratorAvatars = listOf(
                "https://picsum.photos/seed/avatar10/100/100",
                "https://picsum.photos/seed/avatar11/100/100"
            ),
            extraCollaborators = 3
        )
    )

    val userProfile: UserProfile = UserProfile(
        name = "Elena Moretti",
        location = "Florence, Italy",
        avatarUrl = "https://picsum.photos/seed/elena/400/400",
        savedCount = 124,
        boardCount = 18,
        visitedCount = 42,
        travelStyles = listOf("Slow Travel", "Art & Culture", "Boutique Stays", "Gastronomy")
    )
}
