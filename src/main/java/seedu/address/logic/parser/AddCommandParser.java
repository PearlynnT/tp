package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAJOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SOCIAL_MEDIA_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Description;
import seedu.address.model.person.Email;
import seedu.address.model.person.Major;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tutorial;
import seedu.address.model.person.Year;
import seedu.address.model.socialmedialink.SocialMediaLink;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_MAJOR, PREFIX_YEAR, PREFIX_EMAIL,
                        PREFIX_DESCRIPTION, PREFIX_TUTORIAL, PREFIX_SOCIAL_MEDIA_LINK);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_MAJOR, PREFIX_YEAR, PREFIX_EMAIL,
                PREFIX_DESCRIPTION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_MAJOR, PREFIX_YEAR,
                PREFIX_EMAIL, PREFIX_TUTORIAL, PREFIX_DESCRIPTION);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Major major = ParserUtil.parseMajor(argMultimap.getValue(PREFIX_MAJOR).get());
        Year year = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        List<Tutorial> tutorialList = ParserUtil.parseTutorials(argMultimap.getAllValues(PREFIX_TUTORIAL));
        Set<SocialMediaLink> socialMediaLinkList = ParserUtil.parseSocialMediaLinks(
                argMultimap.getAllValues(PREFIX_SOCIAL_MEDIA_LINK));

        Person person = new Person(name, major, year, email, description, tutorialList, socialMediaLinkList);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
