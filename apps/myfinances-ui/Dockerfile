FROM trion/ng-cli as builder

WORKDIR /app
COPY package.json package.json
COPY package-lock.json package-lock.json
RUN npm config set unsafe-perm true
RUN npm ci  --debug
COPY . .
RUN ng build --prod


FROM nginx:1.17.5
EXPOSE 80
COPY default.conf.template /etc/nginx/conf.d/default.conf.template
COPY nginx.conf /etc/nginx/nginx.conf
COPY --from=builder  /app/dist/myfinances-ui /usr/share/nginx/html
CMD /bin/bash -c "envsubst '\$PORT' < /etc/nginx/conf.d/default.conf.template > /etc/nginx/conf.d/default.conf" && nginx -g 'daemon off;'
