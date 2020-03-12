# Mason Check-in Kiosk

![CI/CD](https://github.com/MasonAmerica/mason-check-in-kiosk/workflows/CI/CD/badge.svg)

The check-in kiosk supports dynamically configured fields, NDA signatures, and guest arrival
notifications.

## Simple setup

If you just want to start using the app, this is the quickest way to get going.

### Configure console options

1. Log in to the Console [here](https://mason-check-in-kiosk.firebaseapp.com/).
1. Connect your G Suite and Slack accounts.
   - Ensure API access is enabled in your G Suite Security settings.
1. Enter your company name and logo.
1. Create an [eSignatures account](https://esignatures.io/dashboard).
1. Create an NDA template an input your API Key and template ID into the Console.
1. Hit save.

### Get and install the app

Download the `outputs` artifact from the
[latest build](https://github.com/MasonAmerica/mason-check-in-kiosk/actions?query=workflow%3ACI%2FCD+branch%3Amaster).
You'll find an APK in there which you can either install on your own device or register and deploy
to the Mason Platform.

When the app starts up, log in with the credentials you created in the Console.

## Full setup

Follow this guide if you want to recreate this app with full ownership of all infrastructure and
services, including billing accounts.

### Create your Firebase project

1. Follow [the instructions](https://firebase.google.com/docs/android/setup#console) to add an
   Android app to a Firebase project.
   - Add two apps: `support.bymason.kiosk.checkin` and `support.bymason.kiosk.checkin.debug`.
   - Put your `google-services.json` in `app/google-services.json`.
1. Create [the website](https://console.firebase.google.com/project/_/hosting).
   - Replace the credentials in the
     [Firebase init block](https://github.com/MasonAmerica/mason-check-in-kiosk/blob/a6934f3dc1152ae6003299a2af9223cc3bd19043/web/site/src/main/kotlin/support/bymason/kiosk/checkin/Index.kt#L18-L25).
1. Enable the Blaze [billing plan](https://console.firebase.google.com/project/_/usage/details).
1. Update the `web/.firebaserc` file to point to your project ID.
1. Do a find and replace for `mason-check-in-kiosk.firebaseapp.com` and use your project ID instead.
1. Install the [Firebase CLI](https://firebase.google.com/docs/cli#install_the_firebase_cli).

### Create various integrations

The check-in kiosk integrates with multiple third-party services, each of which needs to be using
your credentials.

#### G Suite

1. Enable the
   [Admin SDK API](https://console.cloud.google.com/flows/enableapi?apiid=admin.googleapis.com).
1. Create Web application
   [credentials](https://console.cloud.google.com/apis/credentials/oauthclient).
   - Add the redirect URL: `https://your-project-id.firebaseapp.com/redirect/gsuite/auth`.
1. Use your client ID in the
   [G Suite Button](https://github.com/MasonAmerica/mason-check-in-kiosk/blob/a6934f3dc1152ae6003299a2af9223cc3bd19043/web/site/src/main/kotlin/support/bymason/kiosk/checkin/Index.kt#L66).
1. Register your OAuth Client ID and secret in the Firebase Functions environment:
   - Run `firebase functions:config:set gsuite.client_id="$CLIENT_ID"`.
   - Run `firebase functions:config:set gsuite.client_secret="$CLIENT_SECRET"`.

#### Slack

1. Create a [Slack app](https://api.slack.com/apps/new).
1. Use your client ID in the
   [Slack button](https://github.com/MasonAmerica/mason-check-in-kiosk/blob/a6934f3dc1152ae6003299a2af9223cc3bd19043/web/site/src/main/kotlin/support/bymason/kiosk/checkin/Index.kt#L74).
1. Register your Slack Client ID and secret in the Firebase Functions environment:
   - Run `firebase functions:config:set slack.client_id="$CLIENT_ID"`.
   - Run `firebase functions:config:set slack.client_secret="$CLIENT_SECRET"`.
1. Under the `OAuth & Permissions` section, add redirect URLs and scopes:
   - Add redirect URL to our service: `https://your-project-id.firebaseapp.com/redirect/slack/auth`.
   - Add the `chat:write`, `users:read`, and `users:read.email` bot token scopes.

#### Twilio

1. Register your Twilio
   [account SID and token](https://www.twilio.com/console) in the Firebase Functions environment:
   - Run `firebase functions:config:set twilio.sid="$ACCOUNT_SID"`.
   - Run `firebase functions:config:set twilio.token="$ACCOUNT_TOKEN"`.
1. Create a [phone number](https://www.twilio.com/console/phone-numbers/incoming) and use it for
   [sending SMS notifications](https://github.com/MasonAmerica/mason-check-in-kiosk/blob/a6934f3dc1152ae6003299a2af9223cc3bd19043/web/server/src/main/kotlin/support/bymason/kiosk/checkin/functions/HostNotifications.kt#L146).

### Deploy the backend and console

Run `./gradlew deploy`. Everything should be live now!

### Build the app

Run `./gradlew assembleRelease`.

### Follow the simple setup

Now that you have your own infrastructure for everything, you'll be able to follow the
[simple setup guide](#simple-setup) with your own Console and app.
