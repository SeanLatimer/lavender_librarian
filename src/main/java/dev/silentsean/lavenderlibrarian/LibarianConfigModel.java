package dev.silentsean.lavenderlibrarian;
import blue.endless.jankson.Comment;
import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;

import java.util.ArrayList;
import java.util.List;

@Modmenu(modId = LavenderLibrarian.MOD_ID)
@Config(name = "lavender-librarian", wrapperName = "LibrarianConfig")
public class LibarianConfigModel {
    @Comment("List of book identifiers, representing the portion after the colon in namespaced IDs (e.g., 'mymod:book_id' → 'book_id').")
    public List<String> bookIdentifiers = new ArrayList<>();
}
