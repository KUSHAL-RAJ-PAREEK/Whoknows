package com.krp.whoknows.Appui.Profile.presentation

import android.R.attr.text
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

/**
 * Created by KUSHAL RAJ PAREEK on 13,March,2025
 */

class SearchViewModel : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _selectedStates = MutableStateFlow(mutableMapOf<String, Boolean>())
    val selectedStates = _selectedStates.asStateFlow()

    private val _interest = MutableStateFlow(allInterest)

    @OptIn(FlowPreview::class)
    val interest = searchText
        .debounce(500L)
        .onEach { _isSearching.update { true } }
        .combine(_interest) { text, interest ->
            if (text.isBlank()) {
                interest
            } else {
                interest.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }.onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _interest.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun toggleSelection(interest: String) {
        _selectedStates.update { currentState ->
            currentState.toMutableMap().apply {
                this[interest] = !(this[interest] ?: false)
            }
        }
    }

}

data class Interest(
    val interest: String
) {
    fun doesMatchSearchQuery(query: String): Boolean {
        return interest.contains(query, ignoreCase = true)
    }

}

private val allInterest = listOf(
    Interest("Gyming"),
    Interest("Working Out"),
    Interest("Fitness"),
    Interest("Weightlifting"),
    Interest("Cardio Training"),
    Interest("Running"),
    Interest("Jogging"),
    Interest("Walking"),
    Interest("Cycling"),
    Interest("Stretching"),
    Interest("Calisthenics"),
    Interest("Zumba"),
    Interest("Aerobics"),
    Interest("Swimming"),
    Interest("Hiking"),
    Interest("Meditation"),
    Interest("Yoga"),
    Interest("Pilates"),
    Interest("Bodybuilding"),
    Interest("Powerlifting"),
    Interest("CrossFit"),
    Interest("Martial Arts"),
    Interest("Judo"),
    Interest("Karate"),
    Interest("Taekwondo"),
    Interest("Boxing"),
    Interest("Kickboxing"),
    Interest("Wrestling"),
    Interest("MMA"),
    Interest("Brazilian Jiu-Jitsu"),
    Interest("Fencing"),
    Interest("Archery"),
    Interest("Competitive Programming"),
    Interest("Software Development"),
    Interest("Mobile App Development"),
    Interest("Game Development"),
    Interest("Web Development"),
    Interest("Data Science"),
    Interest("Machine Learning"),
    Interest("Artificial Intelligence"),
    Interest("Deep Learning"),
    Interest("Cybersecurity"),
    Interest("Ethical Hacking"),
    Interest("Cloud Computing"),
    Interest("Blockchain"),
    Interest("Smart Contracts"),
    Interest("Cryptocurrency"),
    Interest("Stock Market"),
    Interest("Investing"),
    Interest("Trading"),
    Interest("Forex Trading"),
    Interest("Options Trading"),
    Interest("Real Estate"),
    Interest("Entrepreneurship"),
    Interest("Side Hustles"),
    Interest("Freelancing"),
    Interest("Startup Culture"),
    Interest("Small Business"),
    Interest("E-commerce"),
    Interest("Dropshipping"),
    Interest("Affiliate Marketing"),
    Interest("Social Media Marketing"),
    Interest("Digital Marketing"),
    Interest("SEO"),
    Interest("Content Creation"),
    Interest("Blogging"),
    Interest("Vlogging"),
    Interest("Podcasting"),
    Interest("Public Speaking"),
    Interest("Debating"),
    Interest("Writing"),
    Interest("Copywriting"),
    Interest("Technical Writing"),
    Interest("Poetry"),
    Interest("Screenwriting"),
    Interest("Journalism"),
    Interest("Astronomy"),
    Interest("Astrophysics"),
    Interest("Space Exploration"),
    Interest("Rocket Science"),
    Interest("Quantum Physics"),
    Interest("Biotechnology"),
    Interest("Genetics"),
    Interest("Neuroscience"),
    Interest("coding"),
    Interest("Psychology"),
    Interest("Philosophy"),
    Interest("Sociology"),
    Interest("Anthropology"),
    Interest("History"),
    Interest("Archaeology"),
    Interest("Political Science"),
    Interest("Economics"),
    Interest("Personal Finance"),
    Interest("Minimalism"),
    Interest("Mindfulness"),
    Interest("Self-Improvement"),
    Interest("Spirituality"),
    Interest("Esports"),
    Interest("Video Gaming"),
    Interest("Board Games"),
    Interest("Card Games"),
    Interest("Chess"),
    Interest("Go"),
    Interest("Scrabble"),
    Interest("Sudoku"),
    Interest("Puzzles"),
    Interest("Magic Tricks"),
    Interest("Origami"),
    Interest("Calligraphy"),
    Interest("Sketching"),
    Interest("Drawing"),
    Interest("Painting"),
    Interest("Graffiti Art"),
    Interest("Graphic Design"),
    Interest("3D Modeling"),
    Interest("Photography"),
    Interest("Cinematography"),
    Interest("Filmmaking"),
    Interest("Acting"),
    Interest("Theater"),
    Interest("Dancing"),
    Interest("Ballet"),
    Interest("Hip-Hop Dance"),
    Interest("Salsa"),
    Interest("Tango"),
    Interest("Flamenco"),
    Interest("Belly Dancing"),
    Interest("Singing"),
    Interest("Playing Guitar"),
    Interest("Playing Piano"),
    Interest("Playing Violin"),
    Interest("Playing Drums"),
    Interest("Music Production"),
    Interest("DJing"),
    Interest("Beatboxing"),
    Interest("Rapping"),
    Interest("Songwriting"),
    Interest("Listening to Music"),
    Interest("Watching Movies"),
    Interest("TV Shows"),
    Interest("Anime"),
    Interest("Manga"),
    Interest("Korean Dramas"),
    Interest("Cosplay"),
    Interest("Reading Books"),
    Interest("Science Fiction"),
    Interest("Fantasy Books"),
    Interest("Mystery Books"),
    Interest("Thrillers"),
    Interest("Classic Literature"),
    Interest("Cooking"),
    Interest("Baking"),
    Interest("Grilling"),
    Interest("Barbecue"),
    Interest("Vegan Cooking"),
    Interest("Mixology"),
    Interest("Coffee Brewing"),
    Interest("Tea Tasting"),
    Interest("Wine Tasting"),
    Interest("Food Blogging"),
    Interest("Camping"),
    Interest("Backpacking"),
    Interest("Hiking"),
    Interest("Mountain Climbing"),
    Interest("Scuba Diving"),
    Interest("Snorkeling"),
    Interest("Skydiving"),
    Interest("Paragliding"),
    Interest("Bungee Jumping"),
    Interest("Surfing"),
    Interest("Rock Climbing"),
    Interest("Skiing"),
    Interest("Snowboarding"),
    Interest("Ice Skating"),
    Interest("Roller Skating"),
    Interest("Motorcycle Riding"),
    Interest("Road Trips"),
    Interest("Luxury Travel"),
    Interest("Van Life"),
    Interest("Wildlife Watching"),
    Interest("Bird Watching"),
    Interest("Fishing"),
    Interest("Hunting"),
    Interest("Foraging"),
    Interest("Gardening"),
    Interest("Beekeeping"),
    Interest("Pet Training"),
    Interest("Horseback Riding"),
    Interest("Animal Rescue")
)