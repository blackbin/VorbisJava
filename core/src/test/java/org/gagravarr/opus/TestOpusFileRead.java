/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gagravarr.opus;

import java.io.IOException;

import org.gagravarr.ogg.OggFile;

/**
 * Tests for reading things using OpusFile
 */
public class TestOpusFileRead extends AbstractOpusTest {
    public void testRead09() throws IOException {
        OggFile ogg = new OggFile(getTest09File());
        of = new OpusFile(ogg);

        assertEquals(1, of.getInfo().getVersion());
        assertEquals(0, of.getInfo().getMajorVersion());
        assertEquals(1, of.getInfo().getMinorVersion());

        assertEquals(2, of.getInfo().getNumChannels());

        assertEquals(44100, of.getInfo().getRate());
        assertEquals(0x164, of.getInfo().getPreSkip());
        assertEquals(0, of.getInfo().getOutputGain());
        
        assertEquals(0, of.getInfo().getChannelMappingFamily());

        // Tags that are in the 0.9 file and the 1.1 file
        assertEquals("Test Title", of.getTags().getTitle());
        assertEquals("Test Artist", of.getTags().getArtist());
        assertEquals(1, of.getTags().getComments("Comment").size());
        assertEquals("Test Comment", of.getTags().getComments("Comment").get(0));

        // Tags that the 0.9 file misses
        assertEquals(null, of.getTags().getAlbum());
        assertEquals(null, of.getTags().getDate());
        assertEquals(null, of.getTags().getGenre());
        assertEquals(null, of.getTags().getTrackNumber());

        // Has some audio data, but not very much
        OpusAudioData ad = null;

        ad = of.getNextAudioPacket();
        assertNotNull( ad );
        assertEquals(0x579, ad.getGranulePosition());

        ad = of.getNextAudioPacket();
        assertNotNull( ad );
        assertEquals(0x579, ad.getGranulePosition());

        ad = of.getNextAudioPacket();
        assertNull( ad );
    }

    public void testRead11() throws IOException {
        OggFile ogg = new OggFile(getTest11File());
        of = new OpusFile(ogg);

        assertEquals(1, of.getInfo().getVersion());
        assertEquals(0, of.getInfo().getMajorVersion());
        assertEquals(1, of.getInfo().getMinorVersion());

        assertEquals(2, of.getInfo().getNumChannels());

        assertEquals(44100, of.getInfo().getRate());
        assertEquals(0x164, of.getInfo().getPreSkip());
        assertEquals(0, of.getInfo().getOutputGain());

        assertEquals(0, of.getInfo().getChannelMappingFamily());

        // Check the tags
        assertEquals("Test Title", of.getTags().getTitle());
        assertEquals("Test Artist", of.getTags().getArtist());
        assertEquals("Test Album", of.getTags().getAlbum());
        assertEquals("2010-01-26", of.getTags().getDate());
        assertEquals("Test Genre", of.getTags().getGenre());
        assertEquals("1", of.getTags().getTrackNumber());

        assertEquals(2, of.getTags().getComments("Comment").size());
        assertEquals("Test Comment", of.getTags().getComments("Comment").get(0));
        assertEquals("Another Test Comment", of.getTags().getComments("Comment").get(1));

        // Has some audio data, but not very much
        OpusAudioData ad = null;

        ad = of.getNextAudioPacket();
        assertNotNull( ad );
        assertEquals(0x579, ad.getGranulePosition());

        ad = of.getNextAudioPacket();
        assertNotNull( ad );
        assertEquals(0x579, ad.getGranulePosition());

        ad = of.getNextAudioPacket();
        assertNull( ad );
    }
}
