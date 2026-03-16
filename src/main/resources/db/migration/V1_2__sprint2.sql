ALTER TABLE portfolio_images
    ADD COLUMN image_url TEXT;

ALTER TABLE portfolio_images
    ALTER COLUMN media_id DROP NOT NULL;