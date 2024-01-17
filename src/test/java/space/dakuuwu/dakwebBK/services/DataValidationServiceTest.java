package space.dakuuwu.dakwebBK.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import space.dakuuwu.dakwebBK.models.Post;


class DataValidationServiceTest {
    @Mock
    private final DataValidationService dvs = Mockito.spy(new DataValidationService());
    private final Post testPost = new Post("ID1-testingDVS", null, null);

    @Test
    @DisplayName("Test will pass if the method is able to filter invalid title data and return FALSE.")
    void testingValidationForTitleInPosts() {
        //Empty Title
        testPost.setContent("",
                "valid/testUrl.png",
                "dakuuwu.space",
                "Valid Short Description",
                "Valid Long Description, but not required");
        Assertions.assertFalse(dvs.titleFieldValidator(testPost));
        //Null title
        testPost.setContent(null,
                "valid/testUrl.png",
                "dakuuwu.space",
                "Valid Short Description",
                "Valid Long Description, but not required");
        Assertions.assertFalse(dvs.titleFieldValidator(testPost));
    }

    @Test
    @DisplayName("Test will pass if the method is able to filter invalid image URL data and return FALSE.")
    void testingValidationForImageUrlInPosts() {
        //Empty Image URL
        testPost.setContent("Valid title",
                "",
                "dakuuwu.space",
                "Testing for invalid ImageURL in Content",
                "Testing for invalid ImageURL in Content but longer");
        Assertions.assertFalse(dvs.imageUrlFieldValidator(testPost));
        //Null Image URL
        testPost.setContent("Valid title",
                null,
                "dakuuwu.space",
                "Testing for invalid ImageURL in Content",
                "Testing for invalid ImageURL in Content but longer");
        Assertions.assertFalse(dvs.imageUrlFieldValidator(testPost));
    }

    @Test
    @DisplayName("Test will pass if the method is able to filter invalid Short Description data and return FALSE.")
    void testingValidationForShortDescInPosts() {
        //Empty ShortDesc
        testPost.setContent("Valid Title",
                "valid/testUrl.png",
                "dakuuwu.space",
                "",
                "Valid Long Description, but not required");
        Assertions.assertFalse(dvs.shortDescFieldValidator(testPost));
        //Null shortDesc
        testPost.setContent("Valid Title",
                "valid/testUrl.png",
                "dakuuwu.space",
                null,
                "Valid Long Description, but not required");
        Assertions.assertFalse(dvs.shortDescFieldValidator(testPost));
    }

    @Test
    @DisplayName("Test will pass if the method is able to filter invalid Tag data and return FALSE.")
    void testingValidationForTagsInPosts() {
        //Empty Tags
        testPost.setTags(new String[]{});
        Assertions.assertFalse(dvs.tagFieldValidator(testPost));
        //Null Tags
        testPost.setTags(null);
        Assertions.assertFalse(dvs.tagFieldValidator(testPost));
        //Tags with empty values
        testPost.setTags(new String[]{"TestTag1", "Test Tag 2", "", "Test.Tag.4"});
        Assertions.assertFalse(dvs.tagFieldValidator(testPost));
    }

    @Test
    @DisplayName("Test will pass if the method is able to filter an invalid Post and return FALSE for malformed Posts, " +
            "and TRUE for a valid Post.")
    void testingGeneralValidationInPosts() {
        /*
        In the back, the keyFieldValidator method that gets imported from the
        DataValidationService class calls all four individual validations at
        the same time, checking the complete validity of the Post in
        Content title -> Content imageURL -> Content shortDesc -> Tags fashion.
        Checking for ID is not implemented because MongoDB itself handles the _UID creation.
        */
        //Null tags + Null imageURL
        Post.PostContent postCaseOneContent = new Post.PostContent("Valid title",
                null,
                "dakuuwu.space",
                "Valid Description",
                "Valid Description but longer");
        Post postCaseOne = new Post ("postCase1",
                postCaseOneContent,
                null);
        Assertions.assertFalse(dvs.keyFieldValidator(postCaseOne));
        //Invalid imageURL + Empty shortDesc + Empty value in Tags
        Post.PostContent postCaseTwoContent = new Post.PostContent("Valid title",
                "",
                "dakuuwu.space",
                "",
                "Valid Description but longer");
        Post postCaseTwo = new Post ("postCase2",
                postCaseTwoContent,
                new String[]{"TestTag1", "Test Tag 2", "", "Test.Tag.4"});
        Assertions.assertFalse(dvs.keyFieldValidator(postCaseTwo));
        //Null title + Empty (allowed) fields + Empty imageURL
        Post.PostContent postCaseThreeContent = new Post.PostContent(null,
                "",
                "dakuuwu.space",
                "Valid Description",
                "");
        Post postCaseThree = new Post ("postCase3",
                postCaseThreeContent,
                new String[]{"TestTag3"});
        Assertions.assertFalse(dvs.keyFieldValidator(postCaseThree));
        //Healthy Post Check (With two allowed empty values)
        Post.PostContent postCaseFourContent = new Post.PostContent("Valid title",
                "valid/imageURL.png",
                "",
                "Valid Description",
                "");
        Post postCaseFour = new Post ("postCase4",
                postCaseFourContent,
                new String[]{"TestTag1", "TestTag2"});
        Assertions.assertTrue(dvs.keyFieldValidator(postCaseFour));
    }

}