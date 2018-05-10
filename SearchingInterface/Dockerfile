FROM node
RUN mkdir /usr/src/minerva
ADD dist /usr/src/minerva/app
ADD app-server.js /usr/src/minerva/server.js
ADD prodpackage.json /usr/src/minerva/package.json
WORKDIR /usr/src/minerva
RUN npm install
EXPOSE 4200
CMD ["npm", "start"]
