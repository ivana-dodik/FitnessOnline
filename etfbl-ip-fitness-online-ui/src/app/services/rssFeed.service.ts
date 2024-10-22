import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { FeedItem } from '../interfaces/feedItem';
import * as xmlJs from 'xml-js';

@Injectable({
  providedIn: 'root'
})
export class RssFeedService {    

    private URL = "https://feeds.feedburner.com/AceFitFacts";


    constructor(private http: HttpClient) { }
  /*
    getRssFeed(): Observable<FeedItem[]> {
        return this.http.get(this.URL, { responseType: 'text' }).pipe(
          map((xmlData: string) => {
            const json = xmlJs.xml2json(xmlData, { compact: true, spaces: 4 });
            const items = JSON.parse(json).rss.channel.item;
            return items.map((item: any) => ({
              title: item.title._text || '',
              category: item.category._text || '',
              description: item.description._text || '',
              link: item.link._text || ''
            }));
          })
        );
      }*/

      getRssFeed(): Observable<string> {
        return this.http.get(this.URL, { responseType: 'text' });
      }

    /*
    getRssFeed(): Observable<FeedItem[]> {
        return this.http.get(this.URL, { responseType: 'text' }).pipe(
          map((xmlData: string) => {
            const parser = new DOMParser();
            const xmlDoc = parser.parseFromString(xmlData, 'text/xml');
            const items = xmlDoc.getElementsByTagName('item');
            const feedItems: FeedItem[] = [];
            for (let i = 0; i < items.length; i++) {
              const item = items[i];
              const title = item.getElementsByTagName('title')[0]?.textContent || '';
              const category = item.getElementsByTagName('category')[0]?.textContent || '';
              const description = item.getElementsByTagName('description')[0]?.textContent || '';
              const link = item.getElementsByTagName('link')[0]?.textContent || '';
              feedItems.push({ title, category, description, link });
            }
            return feedItems;
          })
        );
      }
      */
}
