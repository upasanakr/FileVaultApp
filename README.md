# FileVaultApp
* CMPE 281 - Project 1 
* University Name: San Jose State University
* Course: Cloud Technologies
* Professor: Sajnay Garje
* Student: Upasana Kumar

Application Introduction
FileVault application resides on the AWS cloud infrastructure, offering authorized users a secure portal for managing their files remotely. Users can effortlessly upload, download, update, and delete their files from anywhere with confidence. This undertaking encompasses a 3-tier web application, making extensive use of diverse AWS resources.This application orchestrates a suite of components to deliver a robust, scalable, and cost-efficient solution for securely backing up data to Amazon S3 and CloudFront. It ensures a seamless user experience even during peak loads, thanks to the adaptive AWS auto-scaling capabilities. Additionally, it effectively manages the health of EC2 instances by harnessing the power of CloudWatch, AWS Lambda, and SNS, all seamlessly integrated with the auto-scale group.

Application link - http://filevaultonline.com/

Application Demo Video :https://vimeo.com/874970646


AWS Resource Architechture

<img width="1115" alt="image" src="https://github.com/upasanakr/FileVaultApp/assets/144417727/5b036494-cddb-47b4-991b-5e9741634837">

*Instructions to run project locally
  Prerequisite Softwares: NodeJS, React
  Clone the code from git
  Run npm install on both the NodeJS and React projects to install all the dependencies.
  Create the .env file with AWS access key and secret in NodeJS project.
  Run the NodeJS API using node src/app.js command.
  Run the react app using npm start command.

*AWS Resources:





 




