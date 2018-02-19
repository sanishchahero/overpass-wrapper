package nice.fontaine.overpass.models.query.settings;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SettingsTest {

    @Test
    public void shouldSetTimeoutAndMaxSize_whenBothAreSet() {
        String expected = "[out:json][timeout:10][maxsize:10000]";

        Settings settings = new Settings.Builder()
                .timeout(10)
                .size(10000L)
                .build();

        assertThat(settings.toQuery()).isEqualTo(expected);
    }

    @Test
    public void shouldTakeDefaultMaxSize_whenNoMaxSizeIsGiven() {
        String expected = "[out:json][timeout:15][maxsize:10485760]";

        Settings settings = new Settings.Builder()
                .timeout(15)
                .build();

        assertThat(settings.toQuery()).isEqualTo(expected);
    }

    @Test
    public void shouldTakeDefaultTimeout_whenNoTimeoutIsGiven() {
        String expected = "[out:json][timeout:10][maxsize:10000]";

        Settings settings = new Settings.Builder()
                .size(10000L)
                .build();

        assertThat(settings.toQuery()).isEqualTo(expected);
    }

    @Test
    public void shouldGetDefaultSettings_whenBuildWithNoInput() {
        String expected = "[out:json][timeout:10][maxsize:10485760]";

        Settings settings = new Settings.Builder()
                .build();

        assertThat(settings.toQuery()).isEqualTo(expected);
    }

    @Test
    public void shouldGetDefaultSettings_whenDefaultSettings() {
        String expected = "[out:json][timeout:10][maxsize:10485760]";

        Settings settings = Settings.getDefault();

        assertThat(settings.toQuery()).isEqualTo(expected);
    }

    @Test
    public void shouldSetGlobalBoundingBox_whenAddToSettings() {
        String expected = "[out:json][timeout:10][maxsize:10485760][bbox:10.0,2.0,11.0,5.0]";

        Settings settings = new Settings.Builder()
                .globalBoundingBox(new double[] {10.0,2.0, 11.0,5.0})
                .build();

        assertThat(settings.toQuery()).isEqualTo(expected);
    }
    
    @Test
    public void getSettingTimeoutNegativParamsTest() {
        assertThatThrownBy(() -> new Settings.Builder()
                .timeout(-1)
                .build()).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Expected timeout to be more than 1 second");
    }

    @Test
    public void getSettingMaxSizeNegativParamsTest() {
        assertThatThrownBy(() -> new Settings.Builder()
                .size(-1L)
                .build()).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Expected maximum size to be more than 1 byte");
    }

    @Test
    public void getSettingMaxSizeOverLimitParamsTest() {
        assertThatThrownBy(() -> new Settings.Builder()
                .size(1073741826L)
                .build()).isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Maximum size must be smaller than");
    }
}
