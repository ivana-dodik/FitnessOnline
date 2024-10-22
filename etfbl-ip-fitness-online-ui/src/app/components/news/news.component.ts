import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FeedItem } from 'src/app/interfaces/feedItem';
import { RssFeedService } from 'src/app/services/rssFeed.service';

import { map } from 'rxjs/operators';
import * as xmlJs from 'xml-js';

@Component({
  selector: 'app-news',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './news.component.html',
  styleUrl: './news.component.css'
})
export class NewsComponent implements OnInit {


  public feedItems: Array<FeedItem> = [];

  constructor(private rssFeedService: RssFeedService) {}

  ngOnInit(): void {
    this.loadRssData();
  }
  public loadRssData() {
    this.rssFeedService.getRssFeed().subscribe((xmlData: string) => {
      const json = xmlJs.xml2json(xmlData, { compact: true, spaces: 4 });
      const items = JSON.parse(json).rss.channel.item;
      this.feedItems = items.map((item: any) => ({
        title: item.title._text || '',
        category: item.category._text || '',
        description: item.description._text || '',
        link: item.link._text || ''
      }));
    });
  }

/*
  public loadRssData() {
    this.rssFeedService.getRssFeed().subscribe((data: FeedItem[]) => {
      this.feedItems = data;
    });
  }*/


   /*
    this.rssFeedService.getRssFeed().subscribe((data: any) => {
      this.feedItems = data.map((i: any) => ({
        title: i.title,
        category: i.category,
        description: i.description,
        link: i.link
      }));
    });
    */

}
