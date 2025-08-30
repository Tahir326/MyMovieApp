package com.example.mymovieapp.data.remote

import com.example.mymovieapp.data.model.Movie

class MockMovieApi {

    private val dataset = createDataset()

    suspend fun fetchMovies(): List<Movie> {
        return dataset.map { it.copy() }
    }

    private fun createDataset(): List<Movie> {
        return listOf(
            // Existing Movies
            Movie("m1", "The Dark Knight", "18/07/2008", 9.0,
                "When the menace known as The Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham.",
                "https://image.tmdb.org/t/p/original/xQPgyZOBhaz1GdCQIPf5A5VeFzO.jpg"
            ),
            Movie("m2", "Inception", "16/07/2010", 8.8,
                "A thief who steals corporate secrets through the use of dream-sharing technology is given the task of planting an idea into the mind of a CEO.",
                "https://m.media-amazon.com/images/I/81p+xe8cbnL._SY679_.jpg"
            ),
            Movie("m3", "Interstellar", "07/11/2014", 8.6,
                "A team of explorers must find a way to ensure humanity's survival after Earth is doomed to become uninhabitable.",
                "https://image.tmdb.org/t/p/original/nCbkOyOMTEwlEV0LtCOvCnwEONA.jpg"
            ),
            Movie("m4", "Avengers: Endgame", "26/04/2019", 8.4,
                "After the devastating events of Avengers: Infinity War, the Avengers assemble once again to reverse Thanos' actions and restore balance to the universe.",
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/ulzhLuWrPK07P1YkdWQLZnQh1JL.jpg"
            ),
            Movie("m5", "Spider-Man: No Way Home", "17/12/2021", 8.3,
                "Peter Parker seeks help from Doctor Strange to erase the memories of everyone who knows he is Spider-Man.",
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg"
            ),
            Movie("m6", "Dune", "22/10/2021", 8.0,
                "A noble family becomes embroiled in a war for control over the galaxy's most valuable asset while its heir becomes troubled by visions of a dark future.",
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/d5NXSklXo0qyIYkgV94XAgMIckC.jpg"
            ),
            Movie("m7", "The Lion King", "19/07/2019", 6.9,
                "Lion cub and future king Simba searches for his identity. His uncle Scar seeks to find his own identity, and his destiny has dire consequences.",
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/dzBtMocZuJbjLOXvrl4zGYigDzh.jpg"
            ),
            Movie("m8", "Guardians of the Galaxy Vol. 3", "05/05/2023", 8.0,
                "Peter Quill and the Guardians must team up with their old friends and new allies to face a powerful foe.",
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/r2J02Z2OpNTctfOSN1Ydgii51I3.jpg"
            ),
            Movie("m9", "Avatar: The Way of Water", "16/12/2022", 7.8,
                "Jake Sully and Neytiri have formed a family and are doing everything to stay together. However, they must leave their home and explore the regions of Pandora.",
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg"
            ),
            Movie("m10", "Mission: Impossible – Fallout", "27/07/2018", 7.7,
                "Ethan Hunt and his IMF team must face a deadly new mission when the group is tasked with recovering stolen nuclear weapons.",
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/AkJQpZp9WoNdj7pLYSj1L0RcMMN.jpg"
            ),
            Movie("m11", "No Time to Die", "30/09/2021", 7.3,
                "James Bond has left active service. His peace is short-lived when Felix Leiter, an old friend from the CIA, turns up asking for help.",
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/iUgygt3fscRoKWCV1d0C7FbM9TP.jpg"
            ),
            Movie("m12", "The Flash", "16/06/2023", 7.4,
                "Barry Allen travels back in time to prevent his mother's murder, which brings unintended consequences to his timeline.",
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/rktDFPbfHfUbArZ6OOOKsXcv0Bm.jpg"
            ),
            Movie("m13", "Shazam", "17/03/2023", 6.7,
                "Shazam and his foster siblings are forced to face the wrath of the gods when an ancient power threatens to destroy the world.",
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/xnopI5Xtky18MPhK40cZAGAOVeV.jpg"
            ),
            Movie("m14", "Deadpool & Wolverine", "06/09/2025", null,
                "Deadpool meets Wolverine in this action-packed and hilarious sequel to the previous Deadpool movies.",
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg"
            ),
            Movie("m15", "Blade", "03/10/2025", null,
                "Blade, a half-vampire, half-human, embarks on a quest to eliminate vampires that threaten humanity.",
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/hx0sdduAsr4rq03RZKZzglR25z7.jpg"
            ),
            Movie("m16", "Fantastic Four", "07/11/2025", null,
                "The Fantastic Four, a group of heroes with unique abilities, fight to save the world from an alien invasion.",
                "https://image.tmdb.org/t/p/w600_and_h900_bestv2/4YMcYEFS8sFuW3soP1HVmgR3cSm.jpg"
            )
        )
    }
}
