package ru.cherry.springhomework.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.CollectionUtils;
import ru.cherry.springhomework.domain.Author;
import ru.cherry.springhomework.domain.Book;
import ru.cherry.springhomework.domain.Comment;
import ru.cherry.springhomework.domain.Genre;
import ru.cherry.springhomework.service.*;

import java.util.List;

@ShellComponent
public class ShellUI {
    private final MessageService messageService;
    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final CommentService commentService;

    public ShellUI(MessageService messageService, BookService bookService, AuthorService authorService, GenreService genreService, CommentService commentService) {
        this.messageService = messageService;
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.commentService = commentService;
    }

    //Книги
    @ShellMethod(value = "Get all books", key = {"gab", "get books"})
    public void gatAllBooks() {
        List<Book> books = bookService.getAllBooks();
        if (!CollectionUtils.isEmpty(books)) {
            messageService.sendMessage("Книги:");
            books.forEach(book -> messageService.sendMessage(book.toString()));
        } else {
            messageService.sendMessage("Ничего не найдено.");
        }
    }

    @ShellMethod(value = "Add book", key = {"adb", "add book"})
    public void addBook() {
        messageService.sendMessage("Введите название книги:");
        String title = messageService.getMessage();
        messageService.sendMessage("Введите автора:");
        String authorName = messageService.getMessage();
        messageService.sendMessage("Введите жанр:");
        String genreName = messageService.getMessage();

        Book book = bookService.addBook(title, authorName, genreName);
        if (null != book) {
            messageService.sendMessage("Книга успешно сохранена.");
        } else {
            messageService.sendMessage("Такая книга уже существует.");
        }
    }

    @ShellMethod(value = "Find book", key = {"fb", "find book"})
    public void findBook() {
        messageService.sendMessage("Введите название книги:");
        String title = messageService.getMessage();
        List<Book> books = bookService.findBook(title);
        if (!CollectionUtils.isEmpty(books)) {
            messageService.sendMessage("Результат поиска:");
            books.forEach(book -> messageService.sendMessage(book.toString()));
        } else {
            messageService.sendMessage("Ничего не найдено.");
        }
    }

    @ShellMethod(value = "Edit book", key = {"eb", "edit book"})
    public void editBook() {
        messageService.sendMessage("Введите идентификатор книги:");
        Long id = messageService.getLongMessage();
        messageService.sendMessage("Введите новое название книги:");
        String newTitle = messageService.getMessage();

        Book book = bookService.editBook(id, newTitle);
        if (null != book) {
            messageService.sendMessage("Книга успешно сохранена.");
        } else {
            messageService.sendMessage("Книга не найдена!");
        }
    }

    @ShellMethod(value = "Delete book", key = {"db", "delete book"})
    public void deleteBook() {
        messageService.sendMessage("Введите идентификатор книги:");
        Long id = messageService.getLongMessage();
        if (Boolean.TRUE.equals(bookService.deleteBook(id))) {
            messageService.sendMessage("Книга успешно удалена. Больше вы её не увидите.");
        } else {
            messageService.sendMessage("Книга не найдена!");
        }
    }

    //Авторы
    @ShellMethod(value = "Get all authors", key = {"gaa", "get authors"})
    public void getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors();
        if (!CollectionUtils.isEmpty(authors)) {
            messageService.sendMessage("Авторы:");
            authors.forEach(author -> messageService.sendMessage(author.toString()));
        } else {
            messageService.sendMessage("Ничего не найдено.");
        }
    }

    @ShellMethod(value = "Add author", key = {"aa", "add author"})
    public void addAuthor() {
        messageService.sendMessage("Введите автора:");
        String name = messageService.getMessage();
        Author author = authorService.getAuthor(name);
        if (null == author) {
            authorService.addAuthor(name);
            messageService.sendMessage("Автор " + name + " успешно сохранен.");
        } else {
            messageService.sendMessage("Такой автор уже существует.");
        }
    }

    @ShellMethod(value = "Get author", key = {"ga", "get author"})
    public void getAuthor() {
        messageService.sendMessage("Введите автора:");
        String name = messageService.getMessage();
        Author author = authorService.getAuthor(name);
        if (null == author) {
            messageService.sendMessage("Автор не найден.");
        } else {
            messageService.sendMessage(author.toString());
        }
    }

    @ShellMethod(value = "Edit author", key = {"ea", "edit author"})
    public void editAuthor() {
        messageService.sendMessage("Введите идентификатор автора:");
        Long id = messageService.getLongMessage();
        messageService.sendMessage("Введите новое имя автора:");
        String newName = messageService.getMessage();

        Author author = new Author(id, newName);
        author = authorService.editAuthor(author);
        if (null != author) {
            messageService.sendMessage("Автор успешно сохранен.");
        } else {
            messageService.sendMessage("Автор не найден!");
        }
    }

    @ShellMethod(value = "Delete author", key = {"da", "delete author"})
    public void deleteAuthor() {
        messageService.sendMessage("Введите идентификатор автора которого хотите удалить:");
        Long id = messageService.getLongMessage();
        if (Boolean.TRUE.equals(authorService.deleteAuthor(id))) {
            messageService.sendMessage("Автор успешно удален.");
        } else {
            messageService.sendMessage("Автор не найден!");
        }
    }


    //Жанры
    @ShellMethod(value = "Get genres", key = {"gag", "get genres"})
    public void getAllGenres() {
        List<Genre> genres = genreService.getAllGenres();
        if (!CollectionUtils.isEmpty(genres)) {
            messageService.sendMessage("Жанры:");
            genres.forEach(genre -> messageService.sendMessage(genre.toString()));
        } else {
            messageService.sendMessage("Ничего не найдено.");
        }
    }

    @ShellMethod(value = "Add genre", key = {"ag", "add genre"})
    public void addGenre() {
        messageService.sendMessage("Введите жанр:");
        String name = messageService.getMessage();
        Genre genre = genreService.getGenre(name);
        if (null == genre) {
            genreService.addGenre(name);
            messageService.sendMessage("Автор " + name + " успешно сохранен.");
        } else {
            messageService.sendMessage("Такой автор уже существует.");
        }
    }

    @ShellMethod(value = "Get genre", key = {"gg", "get genre"})
    public void getGenre() {
        messageService.sendMessage("Введите автора:");
        String name = messageService.getMessage();
        Genre genre = genreService.getGenre(name);
        if (null == genre) {
            messageService.sendMessage("Автор не найден.");
        } else {
            messageService.sendMessage(genre.toString());
        }
    }

    @ShellMethod(value = "Edit genre", key = {"eg", "edit genre"})
    public void editGenre() {
        messageService.sendMessage("Введите идентификатор жанра:");
        Long id = messageService.getLongMessage();
        messageService.sendMessage("Введите новое наименование жанра:");
        String newName = messageService.getMessage();

        Genre genre = new Genre(id, newName);
        genre = genreService.editGenre(genre);
        if (null != genre) {
            messageService.sendMessage("Жанр успешно сохранен.");
        } else {
            messageService.sendMessage("Жанр не найден!");
        }
    }

    @ShellMethod(value = "Delete genre", key = {"dg", "delete genre"})
    public void deleteGenre() {
        messageService.sendMessage("Введите идентификатор жанра которого хотите удалить:");
        Long id = messageService.getLongMessage();
        if (Boolean.TRUE.equals(genreService.deleteGenre(id))) {
            messageService.sendMessage("Жанр успешно удален.");
        } else {
            messageService.sendMessage("Жанр не найден!");
        }
    }

    @ShellMethod(value = "Add comment", key = {"ac", "add comment"})
    public void addComment() {
        messageService.sendMessage("Введите идентификатор книги:");
        Long bookId = messageService.getLongMessage();
        messageService.sendMessage("Введите комментарий:");
        String content = messageService.getMessage();
        Comment comment = commentService.save(bookId, content);

        if (null != comment) {
            messageService.sendMessage("Комментарий успешно сохранен.");
        } else {
            messageService.sendMessage("Комментарий не сохранен.");
        }
    }

}
