# README

## GitHub Token Setup

To access the GitHub API used in this application, you need to provide your personal GitHub token. Follow the steps below to set up your token.

### Getting Your GitHub Token

1. Visit the [GitHub Personal Access Tokens](https://docs.github.com/en/enterprise-server@3.9/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens) documentation page to learn how to generate a personal access token.

2. Follow the instructions to create a new token with the necessary scopes for this application.

3. Copy the generated token to use in the next steps.

### Applying Your GitHub Token

1. Open the `build.gradle` file located in the `app` module of your Android project.

2. Look for the section where the GitHub token is set. It should resemble the following:

    ```groovy
    buildConfigField("String", "GITHUB_TOKEN", "\"your_github_token_here\"")
    ```

3. Replace `"your_github_token_here"` with the token you generated.

4. Save the `build.gradle` file.

### Important Note

- **Keep your token secure**: Treat your GitHub token like a password. Do not share it publicly or embed it in source code repositories that are accessible to others.

- **Token Revocation**: If you suspect that your token has been compromised, revoke it immediately on the GitHub website and generate a new one.

## Running the Application

Now that you have set up your GitHub token, you should be able to run the application successfully.

If you encounter any issues or have further questions, refer to the [GitHub API documentation](https://developer.github.com/v3/) or [GitHub Support](https://support.github.com/) for assistance.
