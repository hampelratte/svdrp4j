/*
 * Copyright (c) Henrik Niehaus
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the project (Lazy Bones) nor the names of its
 *    contributors may be used to endorse or promote products derived from this
 *    software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.hampelratte.svdrp.responses.highlevel;

import java.io.Serial;
import java.util.HashMap;

public class GenreTable extends HashMap<Integer, Genre> {

    @Serial
    private static final long serialVersionUID = 1L;

    public GenreTable() {
        for (int i = 0x0; i < 0x0F; i++) {
            put(i, new Genre(i, "undefined", "undefined content"));
        }

        String category = "Movie/Drama";
        put(0x10, new Genre(0x10, category, "movie/drama (general)"));
        put(0x11, new Genre(0x11, category, "detective/thriller"));
        put(0x12, new Genre(0x12, category, "adventure/western/war"));
        put(0x13, new Genre(0x13, category, "science fiction/fantasy/horror"));
        put(0x14, new Genre(0x14, category, "comedy"));
        put(0x15, new Genre(0x15, category, "soap/melodrama/folkloric"));
        put(0x16, new Genre(0x16, category, "romance"));
        put(0x17, new Genre(0x17, category, "serious/classical/religious/historical movie/drama"));
        put(0x18, new Genre(0x18, category, "adult movie/drama"));
        for (int i = 0x19; i <= 0x1E; i++) {
            put(i, new Genre(i, category, "reserved for future use"));
        }
        put(0x1F, new Genre(0x1F, category, "user defined"));

        category = "News/Current affairs";
        put(0x20, new Genre(0x20, category, "news/current affairs (general)"));
        put(0x21, new Genre(0x21, category, "news/weather report"));
        put(0x22, new Genre(0x22, category, "news magazine"));
        put(0x23, new Genre(0x23, category, "documentary"));
        put(0x24, new Genre(0x24, category, "discussion/interview/debate"));
        for (int i = 0x25; i <= 0x2E; i++) {
            put(i, new Genre(i, category, "reserved for future use"));
        }
        put(0x2F, new Genre(0x2F, category, "user defined"));

        category = "Show/Game show";
        put(0x30, new Genre(0x30, category, "show/game show (general)"));
        put(0x31, new Genre(0x31, category, "game show/quiz/contest"));
        put(0x32, new Genre(0x32, category, "variety show"));
        put(0x33, new Genre(0x33, category, "talk show"));
        for (int i = 0x34; i <= 0x3E; i++) {
            put(i, new Genre(i, category, "reserved for future use"));
        }
        put(0x3F, new Genre(0x3F, category, "user defined"));

        category = "Sports";
        put(0x40, new Genre(0x40, category, "sports (general)"));
        put(0x41, new Genre(0x41, category, "special events (Olympic Games, World Cup, etc.)"));
        put(0x42, new Genre(0x42, category, "sports magazines"));
        put(0x43, new Genre(0x43, category, "football/soccer"));
        put(0x44, new Genre(0x44, category, "tennis/squash"));
        put(0x45, new Genre(0x45, category, "team sports (excluding football)"));
        put(0x46, new Genre(0x46, category, "athletics"));
        put(0x47, new Genre(0x47, category, "motor sport"));
        put(0x48, new Genre(0x48, category, "water sport"));
        put(0x49, new Genre(0x49, category, "winter sport"));
        put(0x4A, new Genre(0x4A, category, "equestrian"));
        put(0x4B, new Genre(0x4B, category, "martial sports"));
        for (int i = 0x4C; i <= 0x4E; i++) {
            put(i, new Genre(i, category, "reserved for future use"));
        }
        put(0x4F, new Genre(0x4F, category, "user defined"));

        category = "Children's/Youth programmes";
        put(0x50, new Genre(0x50, category, "children's/youth programmes (general)"));
        put(0x51, new Genre(0x51, category, "pre-school children's programmes"));
        put(0x52, new Genre(0x52, category, "entertainment programmes for 6 to 14"));
        put(0x53, new Genre(0x53, category, "entertainment programmes for 10 to 16"));
        put(0x54, new Genre(0x54, category, "informational/educational/school programmes"));
        put(0x55, new Genre(0x55, category, "cartoons/puppets"));
        for (int i = 0x56; i <= 0x5E; i++) {
            put(i, new Genre(i, category, "reserved for future use"));
        }
        put(0x5F, new Genre(0x5F, category, "user defined"));

        category = "Music/Ballet/Dance";
        put(0x60, new Genre(0x60, category, "music/ballet/dance (general)"));
        put(0x61, new Genre(0x61, category, "rock/pop"));
        put(0x62, new Genre(0x62, category, "serious music/classical music"));
        put(0x63, new Genre(0x63, category, "folk/traditional music"));
        put(0x64, new Genre(0x64, category, "jazz"));
        put(0x65, new Genre(0x65, category, "musical/opera"));
        put(0x66, new Genre(0x66, category, "ballet"));
        for (int i = 0x67; i <= 0x6E; i++) {
            put(i, new Genre(i, category, "reserved for future use"));
        }
        put(0x6F, new Genre(0x6F, category, "user defined"));

        category = "Arts/Culture (without music)";
        put(0x70, new Genre(0x70, category, "arts/culture (without music, general)"));
        put(0x71, new Genre(0x71, category, "performing arts"));
        put(0x72, new Genre(0x72, category, "fine arts"));
        put(0x73, new Genre(0x73, category, "religion"));
        put(0x74, new Genre(0x74, category, "popular culture/traditional arts"));
        put(0x75, new Genre(0x75, category, "literature"));
        put(0x76, new Genre(0x76, category, "film/cinema"));
        put(0x77, new Genre(0x77, category, "experimental film/video"));
        put(0x78, new Genre(0x78, category, "broadcasting/press"));
        put(0x79, new Genre(0x79, category, "new media"));
        put(0x7A, new Genre(0x7A, category, "arts/culture magazines"));
        put(0x7B, new Genre(0x7B, category, "fashion"));
        for (int i = 0x7C; i <= 0x7E; i++) {
            put(i, new Genre(i, category, "reserved for future use"));
        }
        put(0x7F, new Genre(0x7F, category, "user defined"));

        category = "Social/Political issues/Economics";
        put(0x80, new Genre(0x80, category, "social/political issues/economics (general)"));
        put(0x81, new Genre(0x81, category, "magazines/reports/documentary"));
        put(0x82, new Genre(0x82, category, "economics/social advisory"));
        put(0x83, new Genre(0x83, category, "remarkable people"));
        for (int i = 0x84; i <= 0x8E; i++) {
            put(i, new Genre(i, category, "reserved for future use"));
        }
        put(0x8F, new Genre(0x8F, category, "user defined"));

        category = "Education/Science/Factual topics";
        put(0x90, new Genre(0x90, category, "education/science/factual topics (general)"));
        put(0x91, new Genre(0x91, category, "nature/animals/environment"));
        put(0x92, new Genre(0x92, category, "technology/natural sciences"));
        put(0x93, new Genre(0x93, category, "medicine/physiology/psychology"));
        put(0x94, new Genre(0x94, category, "foreign countries/expeditions"));
        put(0x95, new Genre(0x95, category, "social/spiritual sciences"));
        put(0x96, new Genre(0x96, category, "further education"));
        put(0x97, new Genre(0x97, category, "languages"));
        for (int i = 0x98; i <= 0x9E; i++) {
            put(i, new Genre(i, category, "reserved for future use"));
        }
        put(0x9F, new Genre(0x9F, category, "user defined"));

        category = "Leisure hobbies";
        put(0xA0, new Genre(0xA0, category, "leisure hobbies (general)"));
        put(0xA1, new Genre(0xA1, category, "tourism/travel"));
        put(0xA2, new Genre(0xA2, category, "handicraft"));
        put(0xA3, new Genre(0xA3, category, "motoring"));
        put(0xA4, new Genre(0xA4, category, "fitness and health"));
        put(0xA5, new Genre(0xA5, category, "cooking"));
        put(0xA6, new Genre(0xA6, category, "advertisement/shopping"));
        put(0xA7, new Genre(0xA7, category, "gardening"));
        for (int i = 0xA8; i <= 0xAE; i++) {
            put(i, new Genre(i, category, "reserved for future use"));
        }
        put(0xAF, new Genre(0xAF, category, "user defined"));

        category = "Special characteristics";
        put(0xB0, new Genre(0xB0, category, "original language"));
        put(0xB1, new Genre(0xB1, category, "black and white"));
        put(0xB2, new Genre(0xB2, category, "unpublished"));
        put(0xB3, new Genre(0xB3, category, "live broadcast"));
        for (int i = 0xB4; i <= 0xBE; i++) {
            put(i, new Genre(i, category, "reserved for future use"));
        }
        put(0xBF, new Genre(0xBF, category, "user defined"));

        // future use
        for (int i = 0xC0; i <= 0xEF; i++) {
            put(i, new Genre(i, category, "reserved for future use"));
        }
        for (int i = 0xF0; i <= 0xFF; i++) {
            put(i, new Genre(i, category, "user defined"));
        }
    }
}
