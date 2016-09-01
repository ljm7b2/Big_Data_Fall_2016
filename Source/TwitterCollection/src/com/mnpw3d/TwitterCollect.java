package com.mnpw3d;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import twitter4j.FilterQuery;
import twitter4j.HashtagEntity;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterObjectFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;

public class TwitterCollect {
	static BufferedWriter bw;
	static FileWriter fw;

	public static void main(String[] args) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setJSONStoreEnabled(true);
		cb.setOAuthConsumerKey("HKq7xxmTzIHQURiJCTq2F7S63");
		cb.setOAuthConsumerSecret("esGkEi4KG9w5TY9J0s1oDKhSAGXFOn4I4iI9tVPBzAdD7K3HRZ");
		cb.setOAuthAccessToken("796595630-EHolo0o8su2CIATVvo63MIrtHLi7njoNo5XDgSHp");
		cb.setOAuthAccessTokenSecret("urBbRVEa55IrcA3QWxNsArvFqCiVuHtE8UknE7qmK3bWU");
		File txtTwitter = new File("TwitterHealth.txt");
		if (!txtTwitter.exists()) {
			try {
				txtTwitter.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			fw = new FileWriter(txtTwitter, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bw = new BufferedWriter(fw);

		TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

		StatusListener listener = new StatusListener() {
			long count;

			public void onException(Exception arg0) {
				// TODO Auto-generated method stub

			}

			public void onDeletionNotice(StatusDeletionNotice arg0) {
				// TODO Auto-generated method stub

			}

			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub

			}

			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub

			}

			public void onStatus(Status status) {
				// TODO Auto-generated method stub

				String jsonTweet = TwitterObjectFactory.getRawJSON(status);
				try {
					System.out.println(count++ + "\n");
					bw.append(jsonTweet + "\n");
					// bw.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			public void onTrackLimitationNotice(int arg0) {
				// TODO Auto-generated method stub

			}

		};
		FilterQuery fq = new FilterQuery();

		String keywords[] = { "Disease", "diseases", "virus", "bacteria","infection","health", "blood", "injury", "cancer", "tumour", "cold", "cough", "genes", "disorder", "heart", "heartattack", "illness", "mental illness", "healthy", "unhealthy", "bone marrow dementia","bone marrow", "metabolism" , "Alzheimer's", "vaccines", "Alzheimer", "skin", "attacks", "brain", "lungs","yoga", "healing", "medicine", "cure", "zikavirus", "zika", "HIV", "AIDS", "amoeba", "prostate infection", "sinus", "throat","doctors20","mhealth","emr","ehr","HCIT","healthtech","digitalhealth","hcsm","hcr","HealthReform","Healthcosts","occupyhealthcare","healthcareforall","meaningfuluse","ACO","ICD10","hcsmin","ahima","HIMSS","physician","MedEd","mdehr","medicalbilling","patientengagement","nurses","Patient","PatientExperience","HealthTalk"};

		fq.track(keywords);

		twitterStream.addListener(listener);
		twitterStream.filter(fq);

	}
}