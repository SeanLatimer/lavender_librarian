package dev.silentsean.lavenderlibrarian;

import io.wispforest.lavender.Lavender;
import io.wispforest.lavender.book.LavenderBookItem;
import io.wispforest.owo.itemgroup.Icon;
import io.wispforest.owo.itemgroup.OwoItemGroup;
import io.wispforest.owo.itemgroup.OwoItemSettings;
import net.fabricmc.api.ModInitializer;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class LavenderLibrarian implements ModInitializer {
	public static final String MOD_ID = "lavender_librarian";
	public static final String BOOK_NAMESPACE = MOD_ID + "_books";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final dev.silentsean.lavenderlibrarian.LibrarianConfig CONFIG =
			dev.silentsean.lavenderlibrarian.LibrarianConfig.createAndLoad();
	private static final HashMap<Identifier, LavenderBookItem> LOADED_BOOKS = new HashMap<>();

	public static final OwoItemGroup BOOKS_GROUP = OwoItemGroup
			.builder(new Identifier(MOD_ID, "books"), () -> Icon.of(LavenderBookItem.DYNAMIC_BOOK))
			// additional builder configuration goes between these lines
			.build();

	@Override
	public void onInitialize() {
		BOOKS_GROUP.initialize();
		var books = CONFIG.bookIdentifiers();
		for (var book : books) {
			book = book.trim();
			if (book.isEmpty()) {
				LOGGER.warn("Book identifier is empty!");
				continue;
			}
			var bookIdentifier = getBookIdentifier(book);
			if (bookIdentifier == null) {
                LOGGER.warn("Invalid book identifier for {} not found", book);
				continue;
			}

			if(LOADED_BOOKS.containsKey(bookIdentifier)) {
                LOGGER.warn("Duplicate book identifier for {} found", book);
				continue;
			}
			LOADED_BOOKS.put(bookIdentifier, LavenderBookItem.registerForBook(bookIdentifier, new OwoItemSettings().group(BOOKS_GROUP).maxCount(1)));
            LOGGER.debug("Registered {}", bookIdentifier);
		}
	}

	private static Identifier getBookIdentifier(String bookId) {
		return Identifier.of(BOOK_NAMESPACE, bookId);
	}
}